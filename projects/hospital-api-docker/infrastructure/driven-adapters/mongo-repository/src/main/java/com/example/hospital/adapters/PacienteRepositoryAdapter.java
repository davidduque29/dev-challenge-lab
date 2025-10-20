package com.example.hospital.adapters;


import com.example.hospital.adapters.document.PacienteDocument;
import com.example.hospital.adapters.mapper.PacienteMongoMapper;
import com.example.hospital.adapters.repository.PacienteRepository;

import com.example.hospital.model.Paciente;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 🧩 Adaptador que implementa el puerto de dominio usando MongoRepository.
 */
@Service
@RequiredArgsConstructor
public class PacienteRepositoryAdapter implements PacienteRepositoryPort {

    private final PacienteRepository pacienteRepository;
    private final PacienteMongoMapper mapper;

    @Override
    public List<Paciente> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Paciente> findById(String id) {
        return pacienteRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Paciente save(Paciente paciente) {
        PacienteDocument doc = mapper.toDocument(paciente);
        PacienteDocument saved = pacienteRepository.save(doc);
        return mapper.toDomain(saved);
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

