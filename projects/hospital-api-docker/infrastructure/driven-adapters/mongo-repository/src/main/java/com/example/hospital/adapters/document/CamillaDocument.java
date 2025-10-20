package com.example.hospital.adapters.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Representa una camilla en el hospital.
 */
@Document(collection = "camillas")
public class CamillaDocument {

    @Id
    private String id;
    private String estado;
    private String habitacion;
    private String fechaInicio;
    private String fechaFin;
    @DBRef
    private PacienteDocument paciente;

    public CamillaDocument() {
    }

    public CamillaDocument(String id, String estado, String habitacion, String fechaInicio,
                           String fechaFin, PacienteDocument paciente) {
        this.id = id;
        this.estado = estado;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.paciente = paciente;
    }

    // ✅ Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public PacienteDocument getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDocument paciente) {
        this.paciente = paciente;
    }


}

