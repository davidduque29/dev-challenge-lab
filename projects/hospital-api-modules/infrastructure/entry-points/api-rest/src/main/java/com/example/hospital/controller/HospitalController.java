package com.example.hospital.controller;


import com.example.hospital.document.HospitalDocument;
import com.example.hospital.usecase.hospital.HospitalUseCase;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-mongo/hospitals")
public class HospitalController {

    private final HospitalUseCase hospitalService;

    public HospitalController(HospitalUseCase hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<HospitalDocument> getAllHospitals() {
        return hospitalService.obtenerTodosLosHospitales();
    }

    @PostMapping
    public HospitalDocument createHospital(@RequestBody HospitalDocument hospital) {
        return hospitalService.crearHospital(hospital);
    }

    @GetMapping("/city/{city}")
    public List<HospitalDocument> getByCity(@PathVariable String city) {
        return hospitalService.obtenerHospitalesPorCiudad(city);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable String id) {
        hospitalService.eliminarHospital(id);
    }

}

