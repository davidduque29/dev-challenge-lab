package com.example.hospital.usecase.paciente;

import com.example.hospital.document.PacienteDocument;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 💊 Caso de uso para la gestión de Pacientes.
 * Mantiene la lógica funcional del dominio.
 */
@Slf4j
@RequiredArgsConstructor
public class PacienteUseCase {

    private final PacienteRepositoryPort pacienteRepository;

    /**
     * Retorna todos los pacientes.
     */
    public List<PacienteDocument> obtenerTodosLosPacientes() {
        log.info("📋 Obteniendo todos los pacientes...");
        log.info("📍 pacienteRepository es nulo? {}", (pacienteRepository == null));
        System.out.println("use case - Obteniendo todos los pacientes...");
        return pacienteRepository != null ? pacienteRepository.findAll() : Collections.emptyList();
    }

    /**
     * Busca un paciente por ID.
     */
    public Optional<PacienteDocument> obtenerPacientePorId(String id) {
        log.info("🔍 Buscando paciente por ID: {}", id);
        return pacienteRepository.findById(id);
    }

    /**
     * Crea un nuevo paciente.
     */
    public PacienteDocument crearPaciente(PacienteDocument paciente) {
        log.info("🧬 Creando nuevo paciente: {}", paciente.getPrimerNombre());
        return pacienteRepository.save(paciente);
    }

    /**
     * Actualizar un paciente existente según los campos enviados.
     */
    public PacienteDocument actualizarPaciente(
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
                        PacienteDocument actualizado = pacienteRepository.save(p);
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
     * Elimina un paciente.
     */
    public void eliminarPaciente(String id) {
        log.info("🗑️ Eliminando paciente con id: {}", id);
        if (!pacienteRepository.existsById(id)) {
            log.warn("⚠️ Paciente no encontrado con ID: {}", id);
            throw new RuntimeException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
        log.info("✅ Paciente eliminado exitosamente");
    }
}
