package com.example.hospital.document;

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
public class PacienteDocument {

    @Id
    private String id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String documentoIdentidad;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    // Datos médicos básicos
    private String tipoSangre;
    private String genero;
    private String alergias;
    //Estado del paciente
    private String estado; //Estados: "Hospitalizado", "Alta", "En observación"
    private String fechaAlta;
    // Información administrativa
    private String numeroHistoriaClinica; // Código único interno del hospital
    private String eps;
}
