package com.example.hospitalapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Representa un paciente hospitalizado o dado de alta.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pacientes")
public class Paciente {

    @Id
    private String id;
    private String nombres;
    private String apellidos;
    private String documentoIdentidad;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private String fechaAlta;
}
