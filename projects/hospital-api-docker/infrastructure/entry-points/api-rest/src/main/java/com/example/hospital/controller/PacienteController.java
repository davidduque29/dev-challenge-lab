package com.example.hospital.controller;


import com.example.hospital.response.PacienteResponse;
import com.example.hospital.response.PacienteResponseMapper;
import com.example.hospital.usecase.paciente.DarAltaPacienteUseCase;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 🚪 Controlador REST para gestionar operaciones de alta de pacientes.
 */
@Slf4j
@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final DarAltaPacienteUseCase darAltaPacienteUseCase;

    /**
     * Da de alta a un paciente y devuelve un resumen de la operación.
     */
    @PutMapping("/{id}/alta")
    public ResponseEntity<PacienteResponse> darAlta(@PathVariable String id) {
        log.info("➡️ Iniciando alta para paciente ID: {}", id);

        var result = darAltaPacienteUseCase.darAlta(id);
        PacienteResponse response = PacienteResponseMapper.toResponse(result.paciente(), result.camilla());

        log.info("✅ Alta completada para: {} {}", result.paciente().getPrimerNombre(), result.paciente().getPrimerApellido());
        return ResponseEntity.ok(response);
    }
}


