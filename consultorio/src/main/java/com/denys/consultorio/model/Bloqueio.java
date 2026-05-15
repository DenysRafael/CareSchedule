package com.denys.consultorio.model;


import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bloqueio")
public class Bloqueio {

    public Bloqueio() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Medico medico;

    private LocalDate diaNaoPodeInicio;

    private LocalDate diaNaoPodeFim;

    private LocalTime horaNaoPodeInicio;

    private LocalTime horaNaoPodeFim;

    private String motivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getDiaNaoPodeInicio() {
        return diaNaoPodeInicio;
    }

    public void setDiaNaoPodeInicio(LocalDate diaNaoPodeInicio) {
        this.diaNaoPodeInicio = diaNaoPodeInicio;
    }

    public LocalDate getDiaNaoPodeFim() {
        return diaNaoPodeFim;
    }

    public void setDiaNaoPodeFim(LocalDate diaNaoPodeFim) {
        this.diaNaoPodeFim = diaNaoPodeFim;
    }

    public LocalTime getHoraNaoPodeInicio() {
        return horaNaoPodeInicio;
    }

    public void setHoraNaoPodeInicio(LocalTime horaNaoPodeInicio) {
        this.horaNaoPodeInicio = horaNaoPodeInicio;
    }

    public LocalTime getHoraNaoPodeFim() {
        return horaNaoPodeFim;
    }

    public void setHoraNaoPodeFim(LocalTime horaNaoPodeFim) {
        this.horaNaoPodeFim = horaNaoPodeFim;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
