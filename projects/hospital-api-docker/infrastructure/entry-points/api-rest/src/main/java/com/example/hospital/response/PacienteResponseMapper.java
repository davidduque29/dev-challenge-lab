package com.example.hospital.response;


import com.example.hospital.model.Camilla;
import com.example.hospital.model.Paciente;
import com.example.hospital.utils.PacienteUtils;
import lombok.experimental.UtilityClass;

/**
 * 🧩 Mapper para construir el objeto PacienteResponse desde el dominio.
 */
@UtilityClass
public class PacienteResponseMapper {

    public PacienteResponse toResponse(Paciente paciente, Camilla camilla) {
        String fechaInicio = camilla != null ? camilla.getFechaInicio() : null;
        String fechaFin = camilla != null ? camilla.getFechaFin() : paciente.getFechaAlta();
        String tiempoEstancia = PacienteUtils.calcularTiempoEstancia(fechaInicio, fechaFin);

        return PacienteResponse.builder()
                .id(paciente.getId())
                .nombreCompleto(paciente.getPrimerNombre() + " " + paciente.getPrimerApellido())
                .documentoIdentidad(paciente.getDocumentoIdentidad())
                .estado(paciente.getEstado())
                .fechaAlta(paciente.getFechaAlta())
                .habitacion(camilla != null ? camilla.getHabitacion() : null)
                .fechaIngreso(fechaInicio)
                .fechaSalida(fechaFin)
                .tiempoEstancia(tiempoEstancia)
                .build();
    }
}


