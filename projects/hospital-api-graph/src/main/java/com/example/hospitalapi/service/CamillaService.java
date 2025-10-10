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
 * Servicio para la gestión de camillas.
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
     * Obtener todas las camillas.
     */
    public List<Camilla> obtenerTodasLasCamillas() {
        log.info("📋 Obteniendo todas las camillas registradas");
        List<Camilla> lista = camillaRepository.findAll();
        log.info("📤 Total camillas encontradas: {}", lista.size());
        return lista;
    }

    /**
     * Obtiene todas las camillas con estado "Disponible". obtenerTodasLasCamillas
     */
    public List<Camilla> obtenerCamillasDisponibles() {
        log.info("🛏️ Consultando camillas disponibles...");

        try {
            List<Camilla> disponibles = camillaRepository.findAll()
                    .stream()
                    .filter(c -> "Disponible".equalsIgnoreCase(c.getEstado()))
                    .toList();

            log.info("📤 Total camillas disponibles encontradas: {}", disponibles.size());
            return disponibles;

        } catch (Exception e) {
            log.error("❌ Error al obtener camillas disponibles: {}", e.getMessage(), e);
            throw new RuntimeException("Error al obtener camillas disponibles", e);
        }
    }
    /**
     * Buscar una camilla por ID.
     */
    public Optional<Camilla> obtenerCamillaPorId(String idCamilla) {
        log.info("🔍 Buscando camilla con ID: {}", idCamilla);
        Optional<Camilla> camilla = camillaRepository.findById(idCamilla);
        if (camilla.isPresent()) {
            log.info("✅ Camilla encontrada: {}", camilla.get().getHabitacion());
        } else {
            log.warn("⚠️ Camilla con ID {} no encontrada", idCamilla);
        }
        return camilla;
    }

    /**
     * Crear una nueva camilla.
     */
    public Camilla crearCamilla(Camilla camilla) {
        log.info("🛏️ Iniciando creación de camilla: {}", camilla);

        if (camilla == null) {
            log.error("❌ No se puede crear una camilla nula");
            throw new IllegalArgumentException("La camilla no puede ser nula");
        }

        try {
            Camilla guardada = camillaRepository.save(camilla);
            log.info("✅ Camilla creada correctamente con ID: {}", guardada.getId());
            return guardada;
        } catch (Exception e) {
            log.error("❌ Error al guardar la camilla: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear camilla", e);
        }
    }

    /**
     * Actualiza una camilla existente por su ID.
     */
    public Camilla actualizarCamilla(String idCamilla, Camilla camillaActualizada) {
        log.info("♻️ Iniciando actualización de camilla ID: {}", idCamilla);

        if (camillaActualizada == null) {
            log.warn("⚠️ La camilla enviada para actualizar es nula");
            throw new IllegalArgumentException("La camilla no puede ser nula");
        }

        try {
            return camillaRepository.findById(idCamilla)
                    .map(camilla -> {
                        if (camillaActualizada.getEstado() != null) {
                            log.debug("🔄 Actualizando estado: {}", camillaActualizada.getEstado());
                            camilla.setEstado(camillaActualizada.getEstado());
                        }
                        if (camillaActualizada.getHabitacion() != null) {
                            log.debug("🔄 Actualizando habitación: {}", camillaActualizada.getHabitacion());
                            camilla.setHabitacion(camillaActualizada.getHabitacion());
                        }
                        if (camillaActualizada.getPaciente() != null) {
                            log.debug("🔄 Actualizando paciente asociado: {}", camillaActualizada.getPaciente().getNombres());
                            camilla.setPaciente(camillaActualizada.getPaciente());
                        }
                        if (camillaActualizada.getFechaInicio() != null) {
                            log.debug("🔄 Actualizando fechaInicio: {}", camillaActualizada.getFechaInicio());
                            camilla.setFechaInicio(camillaActualizada.getFechaInicio());
                        }
                        if (camillaActualizada.getFechaFin() != null) {
                            log.debug("🔄 Actualizando fechaFin: {}", camillaActualizada.getFechaFin());
                            camilla.setFechaFin(camillaActualizada.getFechaFin());
                        }

                        Camilla guardada = camillaRepository.save(camilla);
                        log.info("✅ Camilla actualizada exitosamente con ID: {}", guardada.getId());
                        return guardada;
                    })
                    .orElseThrow(() -> {
                        log.warn("⚠️ No se encontró camilla con ID {}", idCamilla);
                        return new RuntimeException("Camilla no encontrada con id: " + idCamilla);
                    });

        } catch (Exception e) {
            log.error("❌ Error al actualizar camilla con ID {}: {}", idCamilla, e.getMessage(), e);
            throw new RuntimeException("Error al actualizar camilla", e);
        }
    }

    /**
     * Asignar un paciente a una camilla.
     */
    public Camilla asignarPaciente(String idCamilla, String idPaciente) {
        log.info("🩺 Asignando paciente {} a camilla {}", idPaciente, idCamilla);

        try {
            Optional<Camilla> camillaOpt = camillaRepository.findById(idCamilla);
            Optional<Paciente> pacienteOpt = pacienteRepository.findById(idPaciente);

            if (camillaOpt.isEmpty()) {
                log.warn("⚠️ No se encontró camilla con ID: {}", idCamilla);
                throw new RuntimeException("Camilla no encontrada con id: " + idCamilla);
            }
            if (pacienteOpt.isEmpty()) {
                log.warn("⚠️ No se encontró paciente con ID: {}", idPaciente);
                throw new RuntimeException("Paciente no encontrado con id: " + idPaciente);
            }

            Camilla camilla = camillaOpt.get();
            Paciente paciente = pacienteOpt.get();

            camilla.setPaciente(paciente);
            camilla.setEstado("Ocupada");

            Camilla actualizada = camillaRepository.save(camilla);
            log.info("✅ Paciente {} asignado correctamente a camilla {}", idPaciente, idCamilla);
            return actualizada;

        } catch (Exception e) {
            log.error("❌ Error al asignar paciente a camilla: {}", e.getMessage(), e);
            throw new RuntimeException("Error al asignar paciente a camilla", e);
        }
    }

    /**
     * Liberar una camilla (marcar como disponible).
     */
    public Camilla liberarCamilla(String idCamilla, String fechaFin) {
        log.info("🚪 Liberando camilla {} con fecha {}", idCamilla, fechaFin);

        return camillaRepository.findById(idCamilla)
                .map(c -> {
                    c.setEstado("Disponible");
                    c.setFechaFin(fechaFin);
                    c.setPaciente(null);
                    Camilla actualizada = camillaRepository.save(c);
                    log.info("✅ Camilla liberada correctamente: {}", actualizada.getId());
                    return actualizada;
                })
                .orElseThrow(() -> {
                    log.warn("⚠️ No se encontró camilla con ID: {}", idCamilla);
                    return new RuntimeException("Camilla no encontrada con id: " + idCamilla);
                });
    }


    /**
     * Eliminar una camilla.
     */
    public void eliminarCamilla(String id) {
        log.info("🗑️ Eliminando camilla con ID: {}", id);

        if (!camillaRepository.existsById(id)) {
            log.warn("⚠️ No existe camilla con ID {} para eliminar", id);
            throw new RuntimeException("Camilla no encontrada con id: " + id);
        }

        camillaRepository.deleteById(id);
        log.info("✅ Camilla eliminada correctamente con ID: {}", id);
    }

}
