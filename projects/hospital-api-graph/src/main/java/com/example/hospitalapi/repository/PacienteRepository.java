package com.example.hospitalapi.repository;

import com.example.hospitalapi.model.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
}

