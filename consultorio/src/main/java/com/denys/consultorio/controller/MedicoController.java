package com.denys.consultorio.controller;

import com.denys.consultorio.model.Medico;
import com.denys.consultorio.repository.MedicoRepository;
import com.denys.consultorio.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public List<Medico> findAll(){
        return medicoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Medico> findById(@PathVariable Long id){
        return medicoService.findById(id);
    }

    @PostMapping
    public Medico save(@RequestBody Medico medico){
        return medicoService.save(medico);
    }

    @PutMapping("/{id}")
    public Medico update(@PathVariable Long id, @RequestBody Medico medico){
        return medicoService.update(id, medico);
    }
}
