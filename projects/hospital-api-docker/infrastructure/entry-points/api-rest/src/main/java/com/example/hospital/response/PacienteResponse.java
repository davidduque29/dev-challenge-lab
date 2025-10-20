package com.example.hospital.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 🎯 Modelo de respuesta para la operación de alta de paciente.
 * Incluye los campos más importantes y la duración de la estancia.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponse {

    private String id;
    private String nombreCompleto;
    private String documentoIdentidad;
    private String estado;
    private String fechaAlta;
    private String habitacion;
    private String fechaIngreso;
    private String fechaSalida;
    private String tiempoEstancia; // Ej: "3 días", "5 horas", etc.
}


