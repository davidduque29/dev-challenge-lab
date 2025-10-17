package com.example.hospital.usecase.camilla;

import com.example.hospital.document.CamillaDocument;
import com.example.hospital.document.PacienteDocument;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 🧠 Caso de uso principal para la gestión de camillas.
 * Mantiene la lógica de negocio desacoplada de la infraestructura.
 */
@Slf4j
@RequiredArgsConstructor
public class CamillaUseCase {

    private final CamillaRepositoryPort camillaRepository;
    private final PacienteRepositoryPort pacienteRepository;

    /**
     * Retorna todas las camillas sin importar su estado.
     */
    public List<CamillaDocument> obtenerTodasLasCamillas() {
        log.info("📋 Obteniendo todas las camillas (sin filtro de estado)...");
        List<CamillaDocument> todas = camillaRepository.findAll();
        log.info("📤 Total camillas encontradas: {}", todas.size());
        return todas;
    }

    /**
     * Retorna todas las camillas disponibles (estado = 'Disponible').
     */
    public List<CamillaDocument> obtenerCamillasDisponibles() {
        log.info("🛏️ Buscando camillas con estado 'Disponible'...");
        List<CamillaDocument> disponibles = camillaRepository.findByEstado("Disponible");
        log.info("📤 Total camillas disponibles: {}", disponibles.size());
        return disponibles;
    }

    /**
     * Buscar una camilla específica por ID.
     */
    public Optional<CamillaDocument> obtenerCamillaPorId(String id) {
        log.info("🔍 Buscando camilla por ID: {}", id);
        return camillaRepository.findById(id);
    }

    // =============================
    // 🧍‍♂️ ASIGNACIÓN DE PACIENTE
    // =============================

    /**
     * Asigna un paciente a una camilla disponible.
     */
    public CamillaDocument asignarPaciente(String idCamilla, String idPaciente) {
        log.info("🩺 Asignando paciente {} a camilla {}", idPaciente, idCamilla);
        System.out.println("🩺 Asignando paciente " + idPaciente + " a camilla " + idCamilla);
        try {
            Optional<CamillaDocument> camillaOpt = camillaRepository.findById(idCamilla);
            Optional<PacienteDocument> pacienteOpt = pacienteRepository.findById(idPaciente);

            if (camillaOpt.isEmpty()) {
                log.warn("⚠️ CamillaDocument no encontrada con ID: {}", idCamilla);
                throw new RuntimeException("CamillaDocument no encontrada con ID: " + idCamilla);
            }
            if (pacienteOpt.isEmpty()) {
                log.warn("⚠️ Paciente no encontrado con ID: {}", idPaciente);
                throw new RuntimeException("Paciente no encontrado con ID: " + idPaciente);
            }

            CamillaDocument camilla = camillaOpt.get();
            PacienteDocument paciente = pacienteOpt.get();

            if (!"Disponible".equalsIgnoreCase(camilla.getEstado())) {
                log.warn("🚫 La camilla {} no está disponible, estado actual: {}", idCamilla, camilla.getEstado());
                throw new RuntimeException("La camilla no está disponible para asignación");
            }

            camilla.setPaciente(paciente);
            camilla.setEstado("Ocupada");
            camilla.setFechaInicio(String.valueOf(LocalDate.now()));

            CamillaDocument actualizada = camillaRepository.save(camilla);
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
    public CamillaDocument liberarCamilla(String idCamilla, String fechaFin) {
        log.info("🚪 Iniciando liberación de camilla ID: {}", idCamilla);

        try {
            return camillaRepository.findById(idCamilla)
                    .map(camilla -> {
                        if (camilla.getPaciente() != null) {
                            PacienteDocument paciente = camilla.getPaciente();
                            paciente.setEstado("Alta");
                            paciente.setFechaAlta(fechaFin);
                            pacienteRepository.save(paciente);
                            log.info("📋 Paciente {} dado de alta el {}", paciente.getPrimerNombre(), fechaFin);
                        }

                        camilla.setEstado("Disponible");
                        camilla.setPaciente(null);
                        camilla.setFechaFin(fechaFin);

                        CamillaDocument liberada = camillaRepository.save(camilla);
                        log.info("✅ CamillaDocument {} liberada correctamente", idCamilla);
                        return liberada;
                    })
                    .orElseThrow(() -> {
                        log.warn("⚠️ CamillaDocument no encontrada con ID: {}", idCamilla);
                        return new RuntimeException("CamillaDocument no encontrada con id: " + idCamilla);
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
    public CamillaDocument crearCamilla(CamillaDocument camilla) {
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
