package com.example.hospitalapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/hospitals/{id}")
    public Hospital getHospital(@PathVariable int id) {
        return hospitalService.getHospital(id);
    }

    @GetMapping("/")
    public String home() {
        return "API activa. Usa /api/hospitals o /api/hospitals/{id}";
    }

    @GetMapping("/hospitals/page")
    public List<Hospital> getHospitalsByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return hospitalService.getHospitalsByPage(page, size);
    }

}
