package com.example.hospitalapi.graphql.service;

import com.example.hospitalapi.model.Camilla;
import com.example.hospitalapi.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * MockDataService simula una capa de servicio que provee datos
 * en memoria para probar las consultas y mutaciones GraphQL
 * sin depender todavía de una base de datos real.
 */
@Service
public class MockDataService {

    // Lista que almacena las camillas simuladas en memoria
    private final List<Camilla> camillas = new ArrayList<>();

    /**
     * Constructor: inicializa algunos datos simulados
     * para poder realizar pruebas de consultas GraphQL.
     */
    public MockDataService() {
        // Creamos un paciente hospitalizado (sin fecha de alta)
        Paciente paciente1 = Paciente.builder()
                .id("1") // ✅ ahora tipo String
                .nombres("Juan")
                .apellidos("Pérez")
                .documentoIdentidad("1001234567")
                .fechaNacimiento(new Date(90, 5, 15)) // año base 1900
                .fechaAlta(null)
                .build();

        // Creamos otra paciente ya dada de alta
        Paciente paciente2 = Paciente.builder()
                .id("2") // ✅ ahora tipo String
                .nombres("Laura")
                .apellidos("Gómez")
                .documentoIdentidad("1019876543")
                .fechaNacimiento(new Date(95, 2, 10))
                .fechaAlta("2025-10-09")
                .build();

        // Camilla ocupada
        camillas.add(Camilla.builder()
                .id("1") // ✅ tipo String
                .estado("Ocupada")
                .habitacion("101-A")
                .paciente(paciente1)
                .fechaInicio("2025-10-05")
                .fechaFin(null)
                .build());

        // Camilla disponible
        camillas.add(Camilla.builder()
                .id("2") // ✅ tipo String
                .estado("Disponible")
                .habitacion("102-B")
                .paciente(null)
                .fechaInicio("2025-09-30")
                .fechaFin("2025-10-08")
                .build());
    }

    /**
     * Retorna todas las camillas que están disponibles.
     *
     * @return lista de camillas con estado "Disponible"
     */
    public List<Camilla> obtenerCamillasDisponibles() {
        return camillas.stream()
                .filter(c -> "Disponible".equalsIgnoreCase(c.getEstado()))
                .toList();
    }

    /**
     * Busca una camilla por su ID.
     *
     * @param idCamilla identificador de la camilla
     * @return objeto Camilla si existe, vacío si no se encuentra
     */
    public Optional<Camilla> obtenerCamillaPorId(String idCamilla) { // ✅ cambiado a String
        return camillas.stream()
                .filter(c -> c.getId().equals(idCamilla))
                .findFirst();
    }

    /**
     * Marca una camilla como disponible (simula el alta del paciente).
     *
     * @param idCamilla id de la camilla a liberar
     * @param fechaFin fecha de liberación
     * @return la camilla actualizada
     */
    public Camilla liberarCamilla(String idCamilla, String fechaFin) { // ✅ cambiado a String
        Optional<Camilla> camillaOpt = obtenerCamillaPorId(idCamilla);

        if (camillaOpt.isPresent()) {
            Camilla camilla = camillaOpt.get();

            // Actualiza estado y fechas
            camilla.setEstado("Disponible");
            camilla.setFechaFin(fechaFin);
            camilla.setPaciente(null); // ya no hay paciente asignado

            return camilla;
        }

        // Si no existe, retornamos null (o podríamos lanzar una excepción)
        return null;
    }
}
