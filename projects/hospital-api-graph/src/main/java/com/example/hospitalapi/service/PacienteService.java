package com.example.hospitalapi.service;

import com.example.hospitalapi.model.Paciente;
import com.example.hospitalapi.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Lógica de negocio para gestión de pacientes.
 */
@Service
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Obtener todos los pacientes registrados.
     */
    public List<Paciente> obtenerTodos() {
        log.info("📋 Obteniendo lista completa de pacientes");
        List<Paciente> lista = pacienteRepository.findAll();
        log.info("📤 Total pacientes encontrados: {}", lista.size());
        return lista;
    }

    /**
     * Buscar un paciente por su ID.
     */
    public Optional<Paciente> obtenerPorId(String id) {
        log.info("🔍 Buscando paciente por ID: {}", id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            log.info("✅ Paciente encontrado: {}", paciente.get().getNombres());
        } else {
            log.warn("⚠️ Paciente con ID {} no encontrado", id);
        }
        return paciente;
    }

    /**
     * Crear un nuevo paciente con los campos del modelo.
     */
    public Paciente crearPaciente(String nombres, String apellidos, String documentoIdentidad,
                                  String fechaNacimiento, String fechaAlta) {
        log.info("🩺 Iniciando creación de paciente: {} {}, Documento: {}", nombres, apellidos, documentoIdentidad);
        try {
            Paciente nuevo = new Paciente();
            nuevo.setNombres(nombres);
            nuevo.setApellidos(apellidos);
            nuevo.setDocumentoIdentidad(documentoIdentidad);

            // Parseo de fecha de nacimiento (String → Date)
            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(fechaNacimiento);
                nuevo.setFechaNacimiento(fecha);
                log.debug("📆 Fecha de nacimiento asignada: {}", fecha);
            }

            nuevo.setFechaAlta(fechaAlta);

            Paciente guardado = pacienteRepository.save(nuevo);
            log.info("✅ Paciente creado exitosamente con ID: {}", guardado.getId());
            return guardado;

        } catch (Exception e) {
            log.error("❌ Error al crear paciente: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear paciente", e);
        }
    }

    /**
     * Actualizar un paciente existente.
     */
    public Paciente actualizarPaciente(String id, String nombres, String apellidos,
                                       String documentoIdentidad, String fechaNacimiento, String fechaAlta) {
        log.info("♻️ Iniciando actualización de paciente ID: {}", id);
        try {
            return pacienteRepository.findById(id)
                    .map(p -> {
                        if (nombres != null) {
                            p.setNombres(nombres);
                            log.debug("🔄 Actualizando nombres a: {}", nombres);
                        }
                        if (apellidos != null) {
                            p.setApellidos(apellidos);
                            log.debug("🔄 Actualizando apellidos a: {}", apellidos);
                        }
                        if (documentoIdentidad != null) {
                            p.setDocumentoIdentidad(documentoIdentidad);
                            log.debug("🔄 Actualizando documentoIdentidad a: {}", documentoIdentidad);
                        }
                        if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date fecha = sdf.parse(fechaNacimiento);
                                p.setFechaNacimiento(fecha);
                                log.debug("🔄 Actualizando fechaNacimiento a: {}", fecha);
                            } catch (Exception ex) {
                                log.error("⚠️ Error parseando fechaNacimiento: {}", ex.getMessage());
                            }
                        }
                        if (fechaAlta != null) {
                            p.setFechaAlta(fechaAlta);
                            log.debug("🔄 Actualizando fechaAlta a: {}", fechaAlta);
                        }

                        Paciente actualizado = pacienteRepository.save(p);
                        log.info("✅ Paciente actualizado exitosamente: {}", actualizado.getId());
                        return actualizado;
                    })
                    .orElseThrow(() -> {
                        log.warn("⚠️ Paciente con ID {} no encontrado para actualización", id);
                        return new RuntimeException("Paciente no encontrado con id: " + id);
                    });
        } catch (Exception e) {
            log.error("❌ Error al actualizar paciente con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al actualizar paciente", e);
        }
    }

    /**
     * Eliminar un paciente por su ID.
     */
    public void eliminarPaciente(String id) {
        log.info("🗑️ Iniciando eliminación de paciente con ID: {}", id);
        try {
            if (!pacienteRepository.existsById(id)) {
                log.warn("⚠️ No se encontró paciente con ID {} para eliminar", id);
                throw new RuntimeException("Paciente no encontrado con id: " + id);
            }

            pacienteRepository.deleteById(id);
            log.info("✅ Paciente eliminado exitosamente con ID: {}", id);

        } catch (Exception e) {
            log.error("❌ Error al eliminar paciente con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar paciente con ID: " + id, e);
        }
    }
}
