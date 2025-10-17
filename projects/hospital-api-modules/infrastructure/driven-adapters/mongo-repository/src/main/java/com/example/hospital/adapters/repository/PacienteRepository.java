package com.example.hospital.adapters.repository;


import com.example.hospital.document.PacienteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<PacienteDocument, String> {
}

