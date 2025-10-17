package com.example.hospitalapi.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una camilla en el hospital.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "camillas")
public class CamillaDocument {

    @Id
    private String id;

    private String estado;
    private String habitacion;
    private String fechaInicio;
    private String fechaFin;

    // Relación con Paciente (referencia entre colecciones)
    @DBRef
    private PacienteDocument paciente;
}
