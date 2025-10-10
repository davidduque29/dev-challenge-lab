package com.example.hospitalapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Representa una camilla en el hospital.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "camillas")
public class Camilla {

    @Id
    private String id;

    private String estado;
    private String habitacion;
    private String fechaInicio;
    private String fechaFin;

    // Relación con Paciente (referencia entre colecciones)
    @DBRef
    private Paciente paciente;
}
