package com.example.hospital.adapters;


import com.example.hospital.document.PacienteDocument;
import com.example.hospital.adapters.repository.PacienteRepository;

import com.example.hospital.ports.out.PacienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 🧩 Adaptador que implementa el puerto de dominio usando MongoRepository.
 */
@Service
@RequiredArgsConstructor
public class PacienteRepositoryAdapter implements PacienteRepositoryPort {

    private final PacienteRepository pacienteRepository;

    @Override
    public List<PacienteDocument> findAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<PacienteDocument> findById(String id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public PacienteDocument save(PacienteDocument paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void deleteById(String id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return pacienteRepository.existsById(id);
    }
}

