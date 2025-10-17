package com.example.hospital.ports.out;

import com.example.hospital.document.PacienteDocument;

import java.util.List;
import java.util.Optional;

/**
 * 🎯 Puerto de dominio que define las operaciones necesarias
 * para la persistencia de Pacientes.
 */
public interface PacienteRepositoryPort {
    List<PacienteDocument> findAll();
    Optional<PacienteDocument> findById(String id);
    PacienteDocument save(PacienteDocument paciente);
    void deleteById(String id);
    boolean existsById(String id);
}

