package com.example.hospitalapi.repository;


import com.example.hospitalapi.document.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HospitalRepository extends MongoRepository<Hospital, String> {
    List<Hospital> findByCity(String city);
}

