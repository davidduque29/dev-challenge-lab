package com.example.hospital.adapters;


import com.example.hospital.adapters.mapper.CamillaMongoMapper;
import com.example.hospital.adapters.repository.CamillaRepository;
import com.example.hospital.adapters.document.CamillaDocument;

import com.example.hospital.model.Camilla;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamillaRepositoryAdapter implements CamillaRepositoryPort {

    private final CamillaRepository repository;
    private final CamillaMongoMapper mapper;

    @Override
    public List<Camilla> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Camilla> findByEstado(String estado) {
        return repository.findByEstado(estado)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Camilla> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Camilla> findByPacienteId(String pacienteId) {
        return repository.findByPaciente_Id(pacienteId)
                .map(mapper::toDomain);
    }

    @Override
    public Camilla save(Camilla camilla) {
        CamillaDocument doc = mapper.toDocument(camilla);
        CamillaDocument saved = repository.save(doc);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
