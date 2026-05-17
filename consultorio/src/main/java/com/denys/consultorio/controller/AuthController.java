package com.denys.consultorio.controller;

import com.denys.consultorio.model.Paciente;
import com.denys.consultorio.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Paciente paciente) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(paciente.getEmail(), paciente.getPassword())
        );

        String token = jwtService.gerarToken(paciente.getEmail());
        return ResponseEntity.ok(token);
    }
}