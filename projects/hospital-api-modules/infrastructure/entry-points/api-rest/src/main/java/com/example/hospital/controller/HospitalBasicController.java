package com.example.hospital.controller;


import com.example.hospital.model.Hospital;
import com.example.hospital.usecase.hospital.HospitalBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalBasicController {

    @Autowired
    private HospitalBasicService hospitalBasicService;

    @GetMapping("/hospitals")
    public List<Hospital> getAllHospitals() {
        return hospitalBasicService.getAllHospitals();
    }

    @GetMapping("/hospitals/{id}")
    public Hospital getHospital(@PathVariable int id) {
        return hospitalBasicService.getHospital(id);
    }

    @GetMapping("/")
    public String home() {
        return "API activa. Usa /api/hospitals o /api/hospitals/{id}";
    }

    @GetMapping("/hospitals/page")
    public List<Hospital> getHospitalsByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return hospitalBasicService.getHospitalsByPage(page, size);
    }

}
