package com.denys.consultorio.service;


import com.denys.consultorio.config.SecurityConfig;
import com.denys.consultorio.model.Paciente;
import com.denys.consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente save(Paciente pacienteCadastro) {
        pacienteCadastro.setPassword(passwordEncoder.encode(pacienteCadastro.getPassword()));
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
