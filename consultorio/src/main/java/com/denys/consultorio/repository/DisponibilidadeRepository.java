package com.denys.consultorio.repository;

import com.denys.consultorio.model.Disponibilidade;
import com.denys.consultorio.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
    List<Disponibilidade> findByMedicoAndDiaDaSemana(Medico medico, DayOfWeek diaDaSemana);
}
