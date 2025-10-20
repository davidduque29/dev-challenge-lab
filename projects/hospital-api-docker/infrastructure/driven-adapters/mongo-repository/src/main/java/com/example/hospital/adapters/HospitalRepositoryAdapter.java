package com.example.hospital.adapters;



import com.example.hospital.adapters.document.HospitalDocument;
import com.example.hospital.adapters.mapper.HospitalMongoMapper;
import com.example.hospital.adapters.repository.HospitalRepository;

import com.example.hospital.model.Hospital;
import com.example.hospital.ports.out.HospitalRepositoryPort;
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
public class HospitalRepositoryAdapter implements HospitalRepositoryPort {

    private final HospitalRepository repository;
    private final HospitalMongoMapper mapper;


    @Override
    public List<Hospital> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Hospital> findByCity(String city) {
        return repository.findByCity(city)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Hospital> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Hospital save(Hospital hospital) {
        HospitalDocument doc = mapper.toDocument(hospital);
        HospitalDocument saved = repository.save(doc);
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
