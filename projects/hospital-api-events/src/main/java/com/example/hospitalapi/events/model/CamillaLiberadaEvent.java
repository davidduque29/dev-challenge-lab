package com.example.hospitalapi.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 🛏️ CamillaLiberadaEvent
 * Representa el cambio de estado de una camilla a 'Disponible'.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamillaLiberadaEvent implements Serializable {

    private String camillaId;
    private String pacienteId;
    private String fechaLiberacion;
    private String origen; // Ej: "alta_paciente", "mantenimiento", "traslado"
}
