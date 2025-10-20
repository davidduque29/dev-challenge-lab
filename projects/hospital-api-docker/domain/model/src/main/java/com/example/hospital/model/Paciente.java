package com.example.hospital.model;



import java.util.Objects;

import java.util.Date;


public class Paciente {

    private String id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String documentoIdentidad;
    private Date fechaNacimiento;
    private String tipoSangre;
    private String genero;
    private String alergias;
    private String estado;            // "Hospitalizado", "Alta", "En observación"
    private String fechaAlta;         // Se mantiene String como en el Document
    private String numeroHistoriaClinica;
    private String eps;

    public Paciente() { }

    public Paciente(String id,
                    String primerNombre,
                    String segundoNombre,
                    String primerApellido,
                    String segundoApellido,
                    String documentoIdentidad,
                    Date fechaNacimiento,
                    String tipoSangre,
                    String genero,
                    String alergias,
                    String estado,
                    String fechaAlta,
                    String numeroHistoriaClinica,
                    String eps) {
        this.id = id;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.documentoIdentidad = documentoIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSangre = tipoSangre;
        this.genero = genero;
        this.alergias = alergias;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.numeroHistoriaClinica = numeroHistoriaClinica;
        this.eps = eps;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getAlergias() { return alergias; } // corregir typo si aplica
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }
    public String getNumeroHistoriaClinica() { return numeroHistoriaClinica; }
    public void setNumeroHistoriaClinica(String numeroHistoriaClinica) { this.numeroHistoriaClinica = numeroHistoriaClinica; }
    public String getEps() { return eps; }
    public void setEps(String eps) { this.eps = eps; }

    // equals / hashCode por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "Paciente{" +
                "id='" + id + '\'' +
                ", primerNombre='" + primerNombre + '\'' +
                ", segundoNombre='" + segundoNombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tipoSangre='" + tipoSangre + '\'' +
                ", genero='" + genero + '\'' +
                ", alergias='" + alergias + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaAlta='" + fechaAlta + '\'' +
                ", numeroHistoriaClinica='" + numeroHistoriaClinica + '\'' +
                ", eps='" + eps + '\'' +
                '}';
    }
}
