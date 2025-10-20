package com.example.hospital.ports.out;


import com.example.hospital.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepositoryPort {

    List<Paciente> findAll();

    Optional<Paciente> findById(String id);

    Paciente save(Paciente paciente);

    void deleteById(String id);

    boolean existsById(String id);
}


