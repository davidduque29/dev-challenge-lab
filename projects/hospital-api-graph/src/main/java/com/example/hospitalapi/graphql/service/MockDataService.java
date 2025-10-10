package com.example.hospitalapi.graphql.service;

import com.example.hospitalapi.model.Camilla;
import com.example.hospitalapi.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 🧪 Servicio de datos simulados (Mock) para pruebas locales con GraphQL.
 * Permite probar resolvers sin conexión a MongoDB.
 */
@Service
public class MockDataService {

    private static final Logger log = LoggerFactory.getLogger(MockDataService.class);

    // Simula almacenamiento en memoria
    private final Map<String, Camilla> camillas = new ConcurrentHashMap<>();
    private final Map<String, Paciente> pacientes = new ConcurrentHashMap<>();

    public MockDataService() {
        inicializarDatos();
    }

    /**
     * Inicializa datos simulados al iniciar la aplicación.
     */
    private void inicializarDatos() {
        log.info("🧩 Inicializando datos de prueba en MockDataService...");

        // 📋 Crear pacientes de ejemplo
        Paciente p1 = new Paciente();
        p1.setId(UUID.randomUUID().toString());
        p1.setPrimerNombre("Juan");
        p1.setSegundoNombre("Carlos");
        p1.setPrimerApellido("Pérez");
        p1.setSegundoApellido("Gómez");
        p1.setDocumentoIdentidad("1234567890");
        p1.setGenero("Masculino");
        p1.setTipoSangre("O+");
        p1.setAlergias("Ninguna");
        p1.setEstado("Hospitalizado");
        p1.setNumeroHistoriaClinica("HIST-1001");
        p1.setEps("Salud Total");
        p1.setFechaAlta(null);

        Paciente p2 = new Paciente();
        p2.setId(UUID.randomUUID().toString());
        p2.setPrimerNombre("María");
        p2.setPrimerApellido("López");
        p2.setSegundoApellido("Ramírez");
        p2.setDocumentoIdentidad("987654321");
        p2.setGenero("Femenino");
        p2.setTipoSangre("A+");
        p2.setAlergias("Penicilina");
        p2.setEstado("Hospitalizado");
        p2.setNumeroHistoriaClinica("HIST-1002");
        p2.setEps("Sura EPS");
        p2.setFechaAlta(null);

        pacientes.put(p1.getId(), p1);
        pacientes.put(p2.getId(), p2);

        // 🛏️ Crear camillas de ejemplo
        Camilla c1 = new Camilla();
        c1.setId(UUID.randomUUID().toString());
        c1.setHabitacion("101-A");
        c1.setEstado("Ocupada");
        c1.setPaciente(p1);
        c1.setFechaInicio("2025-10-09");
        c1.setFechaFin(null);

        Camilla c2 = new Camilla();
        c2.setId(UUID.randomUUID().toString());
        c2.setHabitacion("102-B");
        c2.setEstado("Disponible");
        c2.setPaciente(null);
        c2.setFechaInicio(null);
        c2.setFechaFin(null);

        camillas.put(c1.getId(), c1);
        camillas.put(c2.getId(), c2);

        log.info("✅ Datos mock inicializados: {} camillas, {} pacientes", camillas.size(), pacientes.size());
    }

    // =============================
    // 🔍 CONSULTAS
    // =============================

    /**
     * Retorna todas las camillas disponibles.
     */
    public List<Camilla> obtenerCamillasDisponibles() {
        log.info("📋 Listando camillas disponibles (Mock)");
        return camillas.values().stream()
                .filter(c -> "Disponible".equalsIgnoreCase(c.getEstado()))
                .toList();
    }

    /**
     * Busca una camilla por su ID (versión compatible con GraphQL).
     */
    public Optional<Camilla> obtenerCamillaPorId(String idCamilla) {
        log.info("🔍 [Mock] Buscando camilla por ID: {}", idCamilla);
        Camilla camilla = camillas.get(idCamilla);
        return Optional.ofNullable(camilla);
    }

    // =============================
    // 🧍‍♂️ OPERACIONES MOCK
    // =============================

    /**
     * Libera una camilla y marca al paciente como dado de alta.
     */
    public Camilla liberarCamilla(String idCamilla, String fechaFin) {
        log.info("🚪 Liberando camilla (Mock) ID: {}", idCamilla);
        Camilla camilla = camillas.get(idCamilla);

        if (camilla == null) {
            log.warn("⚠️ Camilla no encontrada en MockDataService");
            throw new RuntimeException("Camilla no encontrada con ID: " + idCamilla);
        }

        if (camilla.getPaciente() != null) {
            Paciente paciente = camilla.getPaciente();
            paciente.setEstado("Alta");
            paciente.setFechaAlta(fechaFin);
            camilla.setPaciente(null);
            log.info("🧾 Paciente {} marcado como dado de alta", paciente.getPrimerNombre());
        }

        camilla.setEstado("Disponible");
        camilla.setFechaFin(fechaFin);

        camillas.put(camilla.getId(), camilla);
        log.info("✅ Camilla {} liberada exitosamente (Mock)", camilla.getHabitacion());

        return camilla;
    }
}
