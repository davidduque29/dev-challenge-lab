package com.example.hospital.adapters;


import com.example.hospital.adapters.repository.CamillaRepository;
import com.example.hospital.document.CamillaDocument;

import com.example.hospital.ports.out.CamillaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CamillaRepositoryAdapter implements CamillaRepositoryPort {

    private final CamillaRepository camillaRepository;

    @Override
    public List<CamillaDocument> findAll() {
        return camillaRepository.findAll();
    }

    @Override
    public List<CamillaDocument> findByEstado(String estado) {
        return camillaRepository.findByEstado(estado);
    }

    @Override
    public Optional<CamillaDocument> findById(String id) {
        return camillaRepository.findById(id);
    }

    @Override
    public Optional<CamillaDocument> findByPacienteId(String pacienteId) {
        return camillaRepository.findByPaciente_Id(pacienteId);
    }

    @Override
    public CamillaDocument save(CamillaDocument camilla) {
        return camillaRepository.save(camilla);
    }

    @Override
    public void deleteById(String id) {
        camillaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return camillaRepository.existsById(id);
    }
}