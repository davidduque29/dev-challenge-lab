package com.example.hospital.ports.out;


import com.example.hospital.document.HospitalDocument;


import java.util.List;
import java.util.Optional;

/**
 * 🏥 Puerto de dominio que define las operaciones de persistencia de hospitales.
 */
public interface HospitalRepositoryPort {
    List<HospitalDocument> findAll();
    List<HospitalDocument> findByCity(String city);
    Optional<HospitalDocument> findById(String id);
    HospitalDocument save(HospitalDocument hospital);
    void deleteById(String id);
    boolean existsById(String id);
}

