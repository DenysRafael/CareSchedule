package com.denys.consultorio.service;


import com.denys.consultorio.model.Medico;
import com.denys.consultorio.repository.MedicoRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico save(Medico medicoCadastro) {
        return medicoRepository.save(medicoCadastro);
    }

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> findById(Long id) {
        return medicoRepository.findById(id);
    }

    public Medico update(Long id, Medico medicoAtualizado){
        medicoAtualizado.setId(id);
        return medicoRepository.save(medicoAtualizado);
    }
}
