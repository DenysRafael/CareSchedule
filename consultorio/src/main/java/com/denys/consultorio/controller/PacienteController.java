package com.denys.consultorio.controller;

import com.denys.consultorio.model.Paciente;
import com.denys.consultorio.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> findAll(){
        return pacienteService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Paciente> findById(@PathVariable Long id){
        return pacienteService.findByID(id);
    }

    @PostMapping
    public Paciente save(@RequestBody Paciente pacienteCadastro){
        return pacienteService.save(pacienteCadastro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        pacienteService.deleteByID(id);
        return ResponseEntity.noContent().build();
    }


}
