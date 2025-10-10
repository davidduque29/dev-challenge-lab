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
 * 🏥 Servicio para la gestión de pacientes:
 * - Creación, actualización, consulta y eliminación.
 * - Maneja la lógica de negocio antes de persistir en MongoDB.
 */
@Service
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    // =============================
    // 🔍 MÉTODOS DE CONSULTA
    // =============================

    /**
     * Obtener todos los pacientes registrados.
     */
    public List<Paciente> obtenerTodos() {
        log.info("📋 Obteniendo lista completa de pacientes...");
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
            log.info("✅ Paciente encontrado: {} {}", paciente.get().getPrimerNombre(), paciente.get().getPrimerApellido());
        } else {
            log.warn("⚠️ Paciente con ID {} no encontrado", id);
        }

        return paciente;
    }

    // =============================
    // ➕ CREACIÓN
    // =============================

    /**
     * Crear un nuevo paciente con datos personales, médicos y administrativos.
     */
    public Paciente crearPaciente(
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String documentoIdentidad,
            String fechaNacimiento,
            String tipoSangre,
            String genero,
            String alergias,
            String estado,
            String numeroHistoriaClinica,
            String eps
    ) {
        log.info("🩺 Creando paciente: {} {} {}, Documento: {}", primerNombre, segundoNombre, primerApellido, documentoIdentidad);

        try {
            Paciente nuevo = new Paciente();

            // 🧾 Datos personales
            nuevo.setPrimerNombre(primerNombre);
            nuevo.setSegundoNombre(segundoNombre);
            nuevo.setPrimerApellido(primerApellido);
            nuevo.setSegundoApellido(segundoApellido);
            nuevo.setDocumentoIdentidad(documentoIdentidad);

            // 📅 Fecha de nacimiento
            if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = sdf.parse(fechaNacimiento);
                nuevo.setFechaNacimiento(fecha);
                log.debug("📆 Fecha de nacimiento asignada: {}", fecha);
            }

            // 💉 Datos médicos
            nuevo.setTipoSangre(tipoSangre);
            nuevo.setGenero(genero);
            nuevo.setAlergias(alergias);

            // 🏥 Estado y alta
            nuevo.setEstado(estado != null ? estado : "Hospitalizado");
            nuevo.setFechaAlta(null); // Paciente recién ingresado

            // 🧾 Información administrativa
            nuevo.setNumeroHistoriaClinica(numeroHistoriaClinica);
            nuevo.setEps(eps);

            // 💾 Guardar en MongoDB
            Paciente guardado = pacienteRepository.save(nuevo);
            log.info("✅ Paciente creado exitosamente con ID: {}", guardado.getId());

            return guardado;

        } catch (Exception e) {
            log.error("❌ Error al crear paciente: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear paciente", e);
        }
    }

    // =============================
    // ♻️ ACTUALIZACIÓN
    // =============================

    /**
     * Actualizar un paciente existente según los campos enviados.
     */
    public Paciente actualizarPaciente(
            String id,
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String documentoIdentidad,
            String fechaNacimiento,
            String tipoSangre,
            String genero,
            String alergias,
            String estado,
            String fechaAlta,
            String numeroHistoriaClinica,
            String eps
    ) {
        log.info("♻️ Iniciando actualización de paciente ID: {}", id);

        try {
            return pacienteRepository.findById(id)
                    .map(p -> {
                        // 🔄 Datos personales
                        if (primerNombre != null) p.setPrimerNombre(primerNombre);
                        if (segundoNombre != null) p.setSegundoNombre(segundoNombre);
                        if (primerApellido != null) p.setPrimerApellido(primerApellido);
                        if (segundoApellido != null) p.setSegundoApellido(segundoApellido);
                        if (documentoIdentidad != null) p.setDocumentoIdentidad(documentoIdentidad);

                        // 📅 Fecha de nacimiento
                        if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date fecha = sdf.parse(fechaNacimiento);
                                p.setFechaNacimiento(fecha);
                            } catch (Exception ex) {
                                log.error("⚠️ Error parseando fechaNacimiento: {}", ex.getMessage());
                            }
                        }

                        // 💉 Datos médicos
                        if (tipoSangre != null) p.setTipoSangre(tipoSangre);
                        if (genero != null) p.setGenero(genero);
                        if (alergias != null) p.setAlergias(alergias);

                        // 🏥 Estado y alta
                        if (estado != null) p.setEstado(estado);
                        if (fechaAlta != null) p.setFechaAlta(fechaAlta);

                        // 🧾 Información administrativa
                        if (numeroHistoriaClinica != null) p.setNumeroHistoriaClinica(numeroHistoriaClinica);
                        if (eps != null) p.setEps(eps);

                        // 💾 Guardar cambios
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

    // =============================
    // 🗑️ ELIMINACIÓN
    // =============================

    /**
     * Eliminar un paciente por su ID.
     */
    public void eliminarPaciente(String id) {
        log.info("🗑️ Eliminando paciente con ID: {}", id);

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
