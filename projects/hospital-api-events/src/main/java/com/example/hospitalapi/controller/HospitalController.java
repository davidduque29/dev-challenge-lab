package com.example.hospitalapi.controller;


import com.example.hospitalapi.document.Hospital;
import com.example.hospitalapi.document.PacienteDocument;
import com.example.hospitalapi.service.HospitalService;
import com.example.hospitalapi.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api-mongo/hospitals")
public class HospitalController {

    private final HospitalService service;
    private final PacienteService pacienteService;

    public HospitalController(HospitalService service, PacienteService pacienteService) {
        this.service = service;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return service.getAll();
    }

    @PostMapping
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return service.save(hospital);
    }

    @GetMapping("/city/{city}")
    public List<Hospital> getByCity(@PathVariable String city) {
        return service.findByCity(city);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable String id) {
        service.delete(id);
    }
    @PutMapping("/pacientes/{id}/alta")
    public ResponseEntity<?> darAlta(@PathVariable String id) {
        PacienteDocument paciente = pacienteService.darAlta(id);
        return ResponseEntity.ok(paciente);
    }
}

