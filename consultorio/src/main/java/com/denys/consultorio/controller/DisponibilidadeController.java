package com.denys.consultorio.controller;

import com.denys.consultorio.model.Disponibilidade;
import com.denys.consultorio.service.DisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @PostMapping
    public ResponseEntity<Disponibilidade> cadastrar(@RequestBody Disponibilidade disponibilidade){
        Disponibilidade novoHorario = disponibilidadeService.save(disponibilidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHorario);
    }

    @GetMapping
    public List<Disponibilidade> findAllDisponibilidade(){
        return disponibilidadeService.findAllDisponibilidade();
    }

    @GetMapping("/{id}")
    public Optional<Disponibilidade> findDisponibilidadeById(@PathVariable Long id){
        return disponibilidadeService.findById(id);
    }

    @PutMapping("/{id}")
    public Disponibilidade update(@PathVariable Long id, @RequestBody Disponibilidade disponibilidade){
        return disponibilidadeService.updateDisponibilidade(id, disponibilidade);
    }

    @DeleteMapping("/{id}")
    public void deleteByID (@PathVariable Long id){
        disponibilidadeService.deleteDisponibilidade(id);
    }
}
