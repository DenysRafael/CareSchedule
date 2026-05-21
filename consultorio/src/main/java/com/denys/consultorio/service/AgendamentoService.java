package com.denys.consultorio.service;

import com.denys.consultorio.model.*;
import com.denys.consultorio.repository.AgendamentoRepository;
import com.denys.consultorio.repository.BloqueioRepository;
import com.denys.consultorio.repository.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    public Agendamento save(Agendamento agendamento) {
        DayOfWeek diaDaSemana = agendamento.getData().getDayOfWeek();
        LocalTime horario = agendamento.getHorario();

        if (diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY) {
            throw new RuntimeException("Agendamentos não são permitidos aos sábados ou domingos.");
        }

        if (horario.isBefore(LocalTime.of(9, 0)) ||
                (horario.isAfter(LocalTime.of(11, 29)) && horario.isBefore(LocalTime.of(13, 0))) ||
                horario.isAfter(LocalTime.of(18, 0))) {
            throw new RuntimeException("Horário fora do expediente (9h às 11h30 ou 13h às 18h).");
        }

        LocalDate agora = LocalDate.now();
        LocalDate limiteMinimo = agora.plusDays(4);

        if (agendamento.getData().isBefore(limiteMinimo)) {
            throw new RuntimeException("Os agendamentos devem ser realizados com no minimo 4 dias de antecedencia!");
        }

        List<Agendamento> conflitos = agendamentoRepository.findByMedicoAndDataAndHorario(
                agendamento.getMedico(), agendamento.getData(), agendamento.getHorario());

        if (!conflitos.isEmpty()) {
            throw new RuntimeException("Já existe um agendamento nesse horário!");
        }

        List<Bloqueio> bloqueios = bloqueioRepository.findByMedicoAndDiaNaoPodeInicioLessThanEqualAndDiaNaoPodeFimGreaterThanEqual(
                agendamento.getMedico(), agendamento.getData(), agendamento.getData());

        if (!bloqueios.isEmpty()) {
            throw new RuntimeException("O médico está indisponivel nessa data!");
        }

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento cancelar(Long id) {

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));

        String emailLogado = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!emailLogado.equals(agendamento.getPaciente().getEmail())) {

            throw new RuntimeException("Você não tem permissão para cancelar essa consulta!");

        }

        LocalDate agora = LocalDate.now();

        LocalDate limiteMinimo = agora.plusDays(2);

        if(agendamento.getData().isBefore(limiteMinimo)){
            throw new RuntimeException("Não foi possivel fazer seu cancelamento pois estamos proximos da data de sua consulta, e o minimo de cancelamento é de 2 dias!");
        }

        agendamento.setStatus(StatusAgend.CANCELADO);

        emailService.enviarEmail(agendamento.getPaciente().getEmail(), "Consulta cancelada", "Sua consulta foi cancelada");

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> findAll() {
        String emailLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        return agendamentoRepository.findByPacienteEmail(emailLogado);
    }

    public Optional<Agendamento> findById(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento update(Long id, Agendamento agendamento){
        agendamento.setId(id);
        return agendamentoRepository.save(agendamento);
    }

    public void deleteById(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
