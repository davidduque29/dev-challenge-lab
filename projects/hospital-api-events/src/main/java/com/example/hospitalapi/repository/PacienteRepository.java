package com.example.hospitalapi.repository;

 import com.example.hospitalapi.document.PacienteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<PacienteDocument, String> {
}

