package com.example.hospitalapi.service;

import com.example.hospitalapi.document.Hospital;
import com.example.hospitalapi.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository repository;

    public HospitalService(HospitalRepository repository) {
        this.repository = repository;
    }

    public List<Hospital> getAll() {
        return repository.findAll();
    }

    public Hospital save(Hospital hospital) {
        return repository.save(hospital);
    }

    public List<Hospital> findByCity(String city) {
        return repository.findByCity(city);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
