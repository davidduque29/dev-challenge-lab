package com.example.hospitalapi.repository;

import com.example.hospitalapi.model.Camilla;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CamillaRepository extends MongoRepository<Camilla, String> {
    List<Camilla> findByEstado(String estado);
}

