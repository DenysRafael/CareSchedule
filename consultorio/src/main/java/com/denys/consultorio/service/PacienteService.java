package com.denys.consultorio.service;


import com.denys.consultorio.model.Paciente;
import com.denys.consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente save(Paciente pacienteCadastro) {
        return pacienteRepository.save(pacienteCadastro);
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findByID(Long id) {
        return pacienteRepository.findById(id);
    }


    public void deleteByID (Long id) {
        pacienteRepository.deleteById(id);
    }







}
