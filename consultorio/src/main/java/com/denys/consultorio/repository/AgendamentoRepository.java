package com.denys.consultorio.repository;

import com.denys.consultorio.model.Agendamento;
import com.denys.consultorio.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByMedicoAndDataAndHorario(Medico medico, LocalDate data, LocalTime horario);
    List<Agendamento> findByPacienteEmail(String email);
}
