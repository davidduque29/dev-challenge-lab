package com.example.hospital.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa una camilla en el dominio del hospital.
 * Esta clase no tiene dependencias de frameworks ni anotaciones externas.
 */
public class Camilla implements Serializable {

    private String id;
    private String estado;
    private String habitacion;
    private String fechaInicio;
    private String fechaFin;
    private Paciente paciente;

    public Camilla() {
    }

    public Camilla(String id, String estado, String habitacion,
                   String fechaInicio, String fechaFin,
                   Paciente paciente) {
        this.id = id;
        this.estado = estado;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.paciente = paciente;
    }

    // Getters y Setters
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Camilla)) return false;
        Camilla camilla = (Camilla) o;
        return Objects.equals(id, camilla.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "Camilla{" +
                "id='" + id + '\'' +
                ", estado='" + estado + '\'' +
                ", habitacion='" + habitacion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", paciente=" + (paciente != null ? paciente.getId() : "null") +
                '}';
    }
}
