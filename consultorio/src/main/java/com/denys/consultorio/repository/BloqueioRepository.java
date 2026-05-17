package com.denys.consultorio.repository;

import com.denys.consultorio.model.Bloqueio;
import com.denys.consultorio.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BloqueioRepository extends JpaRepository<Bloqueio,Long> {
    List<Bloqueio> findByMedicoAndDiaNaoPodeInicioLessThanEqualAndDiaNaoPodeFimGreaterThanEqual(Medico medico, LocalDate data1, LocalDate data2);
}
