package com.denys.consultorio.service;

import com.denys.consultorio.model.Disponibilidade;
import com.denys.consultorio.repository.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadeService {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    public Disponibilidade save(Disponibilidade disponibilidade) {
        return disponibilidadeRepository.save(disponibilidade);
    }

    public List<Disponibilidade> findAllDisponibilidade(){
        return disponibilidadeRepository.findAll();
    }

    public Optional<Disponibilidade> findById(Long id) {
        return disponibilidadeRepository.findById(id);
    }

    public Disponibilidade updateDisponibilidade(Long id, Disponibilidade disponibilidade){
        disponibilidade.setId(id);
        return disponibilidadeRepository.save(disponibilidade);
    }

    public void deleteDisponibilidade(Long id){
        disponibilidadeRepository.deleteById(id);
    }

}
