package com.denys.consultorio.service;


import com.denys.consultorio.model.Bloqueio;
import com.denys.consultorio.repository.BloqueioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BloqueioService {

    @Autowired
    private BloqueioRepository bloqueioRepository;

    public Bloqueio save(Bloqueio bloqueio){
        return bloqueioRepository.save(bloqueio);
    }

    public List<Bloqueio> findAllBloqueio(){
        return bloqueioRepository.findAll();
    }

    public Optional<Bloqueio> findBloqueioById(Long id){
        return bloqueioRepository.findById(id);
    }

    public Bloqueio update(Long id, Bloqueio bloqueio){
        bloqueio.setId(id);
        return bloqueioRepository.save(bloqueio);
    }

    public void deleteById(Long id){
        bloqueioRepository.deleteById(id);
    }
}
