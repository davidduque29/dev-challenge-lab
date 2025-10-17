package com.example.hospital.adapters;



import com.example.hospital.document.HospitalDocument;
import com.example.hospital.adapters.repository.HospitalRepository;

import com.example.hospital.ports.out.HospitalRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 🧩 Adaptador que implementa el puerto de dominio usando MongoRepository.
 */
@Service
@RequiredArgsConstructor
public class HospitalRepositoryAdapter implements HospitalRepositoryPort {

    private final HospitalRepository hospitalRepository;

    @Override
    public List<HospitalDocument> findAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public List<HospitalDocument> findByCity(String city) {
        return hospitalRepository.findByCity(city);
    }

    @Override
    public Optional<HospitalDocument> findById(String id) {
        return hospitalRepository.findById(id);
    }

    @Override
    public HospitalDocument save(HospitalDocument hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public void deleteById(String id) {
        hospitalRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return hospitalRepository.existsById(id);
    }
}

