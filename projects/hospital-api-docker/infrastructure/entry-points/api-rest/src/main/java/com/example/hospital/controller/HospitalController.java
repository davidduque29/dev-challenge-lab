package com.example.hospital.controller;

import com.example.hospital.model.Hospital;
import com.example.hospital.usecase.hospital.HospitalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 🏥 Controlador REST para la gestión de hospitales.
 * Desacoplado de la capa de persistencia.
 */
@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalUseCase hospitalUseCase;

    /**
     * Obtiene todos los hospitales registrados.
     */
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalUseCase.obtenerTodosLosHospitales();
    }

    /**
     * Crea un nuevo hospital.
     */
    @PostMapping
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalUseCase.crearHospital(hospital);
    }

    /**
     * Obtiene los hospitales por ciudad.
     */
    @GetMapping("/city/{city}")
    public List<Hospital> getByCity(@PathVariable String city) {
        return hospitalUseCase.obtenerHospitalesPorCiudad(city);
    }

    /**
     * Elimina un hospital por su ID.
     */
    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable String id) {
        hospitalUseCase.eliminarHospital(id);
    }
}
