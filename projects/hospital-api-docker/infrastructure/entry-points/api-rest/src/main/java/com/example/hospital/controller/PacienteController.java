package com.example.hospital.controller;



import com.example.hospital.document.PacienteDocument;
import com.example.hospital.usecase.paciente.DarAltaPacienteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api-mongo/hospitals")
public class PacienteController {

    private final DarAltaPacienteUseCase darAltaPacienteUseCase;

    public PacienteController(DarAltaPacienteUseCase darAltaPacienteUseCase) {
        this.darAltaPacienteUseCase = darAltaPacienteUseCase;
    }

    @PutMapping("/pacientes/{id}/alta")
    public ResponseEntity<PacienteDocument> darAlta(@PathVariable String id) {
        System.out.println("➡️ Iniciando alta para paciente ID: " + id);
        PacienteDocument paciente = darAltaPacienteUseCase.darAlta(id);
        System.out.println("✅ Alta completada para: " + paciente.getPrimerNombre());
        return ResponseEntity.ok(paciente);
    }

}

