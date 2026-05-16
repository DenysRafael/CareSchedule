package com.denys.consultorio.controller;


import com.denys.consultorio.model.Agendamento;
import com.denys.consultorio.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public List<Agendamento> findAll(){
        return agendamentoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Agendamento> findById(@PathVariable Long id){
        return agendamentoService.findById(id);
    }

    @PostMapping
    public Agendamento save(@RequestBody Agendamento agendamento){
        return agendamentoService.save(agendamento);
    }

    @PutMapping("/{id}")
    public Agendamento update(@PathVariable Long id, Agendamento agendamento){
        return agendamentoService.update(id, agendamento);
    }

    @PatchMapping("/{id}")
    public Agendamento cancelar(@PathVariable Long id){
        return agendamentoService.cancelar(id);
    }
}
