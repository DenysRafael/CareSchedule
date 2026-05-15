package com.denys.consultorio.controller;


import com.denys.consultorio.model.Bloqueio;
import com.denys.consultorio.service.BloqueioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bloqueio")
public class BloqueioController {

    @Autowired
    private BloqueioService bloqueioService;

    @PostMapping
    public ResponseEntity<Bloqueio> Bloqueio(@RequestBody Bloqueio bloqueio){
        Bloqueio pegaHorario = bloqueioService.save(bloqueio);
        return ResponseEntity.status(HttpStatus.CREATED).body(pegaHorario);
    }

    @GetMapping
    public List<Bloqueio> findAllBloqueio(){
        return bloqueioService.findAllBloqueio();
    }

    @GetMapping("/{id}")
    public Optional<Bloqueio> findBloqueioById(@PathVariable Long id){
        return bloqueioService.findBloqueioById(id);
    }

    @PutMapping("/{id}")
    public Bloqueio updateBloqueio(@PathVariable Long id, @RequestBody Bloqueio bloqueio){
        return bloqueioService.update(id, bloqueio);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        bloqueioService.deleteById(id);
    }
}
