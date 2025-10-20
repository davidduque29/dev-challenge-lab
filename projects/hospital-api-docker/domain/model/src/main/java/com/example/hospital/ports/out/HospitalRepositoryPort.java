package com.example.hospital.ports.out;

import com.example.hospital.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepositoryPort {

    List<Hospital> findAll();

    List<Hospital> findByCity(String city);

    Optional<Hospital> findById(String id);

    Hospital save(Hospital hospital);

    void deleteById(String id);

    boolean existsById(String id);
}


