package com.example.hospital.usecase.paciente;


import com.example.hospital.model.Paciente;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
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
    public List<Paciente> obtenerTodosLosPacientes() {
        log.info("📋 Obteniendo todos los pacientes...");
        return pacienteRepository != null ? pacienteRepository.findAll() : Collections.emptyList();
    }

    /**
     * Busca un paciente por ID.
     */
    public Optional<Paciente> obtenerPacientePorId(String id) {
        log.info("🔍 Buscando paciente por ID: {}", id);
        return pacienteRepository.findById(id);
    }

    /**
     * Crea un nuevo paciente.
     */
    public Paciente crearPaciente(Paciente paciente) {
        log.info("🧬 Creando nuevo paciente: {}", paciente.getPrimerNombre());
        return pacienteRepository.save(paciente);
    }

    /**
     * Actualiza un paciente existente según los campos enviados.
     */
    public Paciente actualizarPaciente(
            String id,
            String alergias,
            String eps,
            String estado
    ) {
        log.info("♻️ Iniciando actualización de paciente ID: {}", id);

        try {
            return pacienteRepository.findById(id)
                    .map(p -> {
                        if (alergias != null) p.setAlergias(alergias);
                        if (eps != null) p.setEps(eps);
                        if (estado != null) p.setEstado(estado);

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
