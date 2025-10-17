package com.example.hospital.adapters.repository;


import com.example.hospital.document.HospitalDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HospitalRepository extends MongoRepository<HospitalDocument, String> {
    List<HospitalDocument> findByCity(String city);
}

