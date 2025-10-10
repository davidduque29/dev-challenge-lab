package com.example.hospitalapi.service;

import com.example.hospitalapi.model.Camilla;
import com.example.hospitalapi.model.Paciente;
import com.example.hospitalapi.repository.CamillaRepository;
import com.example.hospitalapi.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 🏥 Servicio para la gestión de Camillas:
 * - Consultar disponibilidad
 * - Asignar paciente
 * - Liberar camilla (alta del paciente)
 */
@Service
public class CamillaService {

    private static final Logger log = LoggerFactory.getLogger(CamillaService.class);

    private final CamillaRepository camillaRepository;
    private final PacienteRepository pacienteRepository;

    public CamillaService(CamillaRepository camillaRepository, PacienteRepository pacienteRepository) {
        this.camillaRepository = camillaRepository;
        this.pacienteRepository = pacienteRepository;
    }
    /**
     * Retorna todas las camillas sin importar su estado.
     */
    public List<Camilla> obtenerTodasLasCamillas() {
        log.info("📋 Obteniendo todas las camillas (sin filtro de estado)...");
        List<Camilla> todas = camillaRepository.findAll();
        log.info("📤 Total camillas encontradas: {}", todas.size());
        return todas;
    }

    /**
     * Retorna todas las camillas disponibles (estado = "Disponible").
     */
    public List<Camilla> obtenerCamillasDisponibles() {
        log.info("🛏️ Buscando camillas con estado 'Disponible'...");
        List<Camilla> disponibles = camillaRepository.findByEstado("Disponible");
        log.info("📤 Total camillas disponibles: {}", disponibles.size());
        return disponibles;
    }

    /**
     * Buscar una camilla específica por ID.
     */
    public Optional<Camilla> obtenerCamillaPorId(String id) {
        log.info("🔍 Buscando camilla por ID: {}", id);
        return camillaRepository.findById(id);
    }

    // =============================
    // 🧍‍♂️ ASIGNACIÓN DE PACIENTE
    // =============================

    /**
     * Asigna un paciente a una camilla disponible.
     */
    public Camilla asignarPaciente(String idCamilla, String idPaciente) {
        log.info("🩺 Asignando paciente {} a camilla {}", idPaciente, idCamilla);

        try {
            Optional<Camilla> camillaOpt = camillaRepository.findById(idCamilla);
            Optional<Paciente> pacienteOpt = pacienteRepository.findById(idPaciente);

            if (camillaOpt.isEmpty()) {
                log.warn("⚠️ Camilla no encontrada con ID: {}", idCamilla);
                throw new RuntimeException("Camilla no encontrada con ID: " + idCamilla);
            }
            if (pacienteOpt.isEmpty()) {
                log.warn("⚠️ Paciente no encontrado con ID: {}", idPaciente);
                throw new RuntimeException("Paciente no encontrado con ID: " + idPaciente);
            }

            Camilla camilla = camillaOpt.get();
            Paciente paciente = pacienteOpt.get();

            if (!"Disponible".equalsIgnoreCase(camilla.getEstado())) {
                log.warn("🚫 La camilla {} no está disponible, estado actual: {}", idCamilla, camilla.getEstado());
                throw new RuntimeException("La camilla no está disponible para asignación");
            }

            // 🧩 Asociar entidades
            camilla.setPaciente(paciente);
            camilla.setEstado("Ocupada");
            camilla.setFechaInicio(String.valueOf(java.time.LocalDate.now()));

            Camilla actualizada = camillaRepository.save(camilla);
            log.info("✅ Paciente {} asignado correctamente a camilla {}", paciente.getPrimerNombre(), idCamilla);

            return actualizada;

        } catch (Exception e) {
            log.error("❌ Error al asignar paciente a camilla: {}", e.getMessage(), e);
            throw new RuntimeException("Error al asignar paciente a camilla", e);
        }
    }

    // =============================
    // 🚪 LIBERAR CAMILLA
    // =============================

    /**
     * Libera una camilla al dar de alta a un paciente.
     */
    public Camilla liberarCamilla(String idCamilla, String fechaFin) {
        log.info("🚪 Iniciando liberación de camilla ID: {}", idCamilla);

        try {
            return camillaRepository.findById(idCamilla)
                    .map(camilla -> {
                        if (camilla.getPaciente() != null) {
                            Paciente paciente = camilla.getPaciente();
                            paciente.setEstado("Alta");
                            paciente.setFechaAlta(fechaFin);
                            pacienteRepository.save(paciente);
                            log.info("📋 Paciente {} dado de alta el {}", paciente.getPrimerNombre(), fechaFin);
                        }

                        camilla.setEstado("Disponible");
                        camilla.setPaciente(null);
                        camilla.setFechaFin(fechaFin);

                        Camilla liberada = camillaRepository.save(camilla);
                        log.info("✅ Camilla {} liberada correctamente", idCamilla);
                        return liberada;
                    })
                    .orElseThrow(() -> {
                        log.warn("⚠️ Camilla no encontrada con ID: {}", idCamilla);
                        return new RuntimeException("Camilla no encontrada con id: " + idCamilla);
                    });

        } catch (Exception e) {
            log.error("❌ Error al liberar camilla: {}", e.getMessage(), e);
            throw new RuntimeException("Error al liberar camilla", e);
        }
    }

    // =============================
    // 🏗️ CREACIÓN Y ELIMINACIÓN
    // =============================

    /**
     * Crear una nueva camilla.
     */
    public Camilla crearCamilla(Camilla camilla) {
        log.info("🛠️ Creando nueva camilla: {}", camilla.getHabitacion());
        camilla.setEstado("Disponible");
        return camillaRepository.save(camilla);
    }

    /**
     * Eliminar una camilla por su ID.
     */
    public void eliminarCamilla(String id) {
        log.info("🗑️ Eliminando camilla ID: {}", id);
        if (!camillaRepository.existsById(id)) {
            log.warn("⚠️ Camilla no encontrada con ID: {}", id);
            throw new RuntimeException("Camilla no encontrada con id: " + id);
        }
        camillaRepository.deleteById(id);
        log.info("✅ Camilla eliminada exitosamente");
    }
}
