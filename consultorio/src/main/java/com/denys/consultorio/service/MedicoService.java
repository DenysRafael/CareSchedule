package com.denys.consultorio.service;


import com.denys.consultorio.model.Medico;
import com.denys.consultorio.repository.MedicoRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico save(Medico medicoCadastro) {
        medicoCadastro.setPassword(passwordEncoder.encode(medicoCadastro.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return medicoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Médico não encontrado!"));
    }
}
