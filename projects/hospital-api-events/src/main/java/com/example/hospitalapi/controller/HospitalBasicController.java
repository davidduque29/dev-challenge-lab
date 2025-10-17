package com.example.hospitalapi.controller;

import com.example.hospitalapi.service.HospitalBasicService;
import com.example.hospitalapi.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
