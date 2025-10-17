package com.example.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamillaLiberadaEvent implements Serializable {

    private String camillaId;
    private String pacienteId;
    private String fechaLiberacion;
    private String origen; // Ej: "alta_paciente", "mantenimiento", "traslado"
}