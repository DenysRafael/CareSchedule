package com.denys.consultorio.service;

import com.denys.consultorio.model.*;
import com.denys.consultorio.repository.AgendamentoRepository;
import com.denys.consultorio.repository.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Agendamento save(Agendamento agendamento) {
        DayOfWeek diaDaSemana = agendamento.getData().getDayOfWeek();
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByMedicoAndDiaDaSemana(agendamento.getMedico(), diaDaSemana);

        boolean horarioValido = false;

        for(Disponibilidade d : disponibilidades) {
            if (!agendamento.getHorario().isBefore(d.getHorarioInicio()) && !agendamento.getHorario().isAfter(d.getHorarioFim())) {
                horarioValido = true;
                break;
            }
        }

        LocalDate agora = LocalDate.now();

        LocalDate limiteMinimo = agora.plusDays(4);

        if(agendamento.getData().isBefore(limiteMinimo)){
            throw new RuntimeException("Os agendamentos devem ser realizados com no minimo 4 dias de antecedencia!");
        }

        if (!horarioValido) {
            throw new RuntimeException("Horário fora da disponibilidade do médico!");
        }

        List<Agendamento> conflitos = agendamentoRepository.findByMedicoAndDataAndHorario (agendamento.getMedico(), agendamento.getData(), agendamento.getHorario());

        if(!conflitos.isEmpty()) {
            throw new RuntimeException("Já existe um agendamento nesse horário!");
        }

        return agendamentoRepository.save(agendamento);

    }

    public Agendamento cancelar(Long id) {

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!"));

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
        return agendamentoRepository.findAll();
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
