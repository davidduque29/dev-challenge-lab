package com.example.hospital.utils;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ⏱️ Utilidades para operaciones relacionadas con pacientes.
 */
@Slf4j
@UtilityClass
public class PacienteUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Calcula el tiempo de estancia entre dos fechas en formato ISO o "yyyy-MM-dd".
     */
    public String calcularTiempoEstancia(String fechaInicio, String fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return "Sin información";
        }

        try {
            // Normaliza las fechas: agrega hora si no la tiene
            LocalDateTime inicio = normalizarFecha(fechaInicio);
            LocalDateTime fin = normalizarFecha(fechaFin);

            Duration duracion = Duration.between(inicio, fin);
            long dias = duracion.toDays();
            long horas = duracion.toHours() % 24;
            long minutos = duracion.toMinutes() % 60;

            if (dias > 0) {
                return dias + " día(s) " + horas + " hora(s)";
            } else if (horas > 0) {
                return horas + " hora(s) " + minutos + " minuto(s)";
            } else {
                return minutos + " minuto(s)";
            }

        } catch (Exception e) {
            log.error("⚠️ Error al calcular tiempo de estancia: {}", e.getMessage());
            return "Error en cálculo";
        }
    }

    private LocalDateTime normalizarFecha(String fecha) {
        if (fecha.contains("T")) {
            return LocalDateTime.parse(fecha, FORMATTER);
        }
        return LocalDateTime.parse(fecha + "T00:00:00", FORMATTER);
    }
}

