package com.example.hospitalapi.graphql.mutation;

import com.example.hospitalapi.model.Paciente;
import com.example.hospitalapi.service.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * Resolver GraphQL para mutaciones de pacientes (creación, actualización, eliminación).
 */
@Controller
public class PacienteMutationResolver {

    private static final Logger log = LoggerFactory.getLogger(PacienteMutationResolver.class);

    private final PacienteService pacienteService;

    public PacienteMutationResolver(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Crea un nuevo paciente.
     */
    @MutationMapping
    public Paciente crearPaciente(@Argument String nombres,
                                  @Argument String apellidos,
                                  @Argument String documentoIdentidad,
                                  @Argument String fechaNacimiento,
                                  @Argument String fechaAlta) {
        log.info("✳️ Mutation crearPaciente(nombres={}, apellidos={}, documentoIdentidad={}, fechaNacimiento={}, fechaAlta={})",
                nombres, apellidos, documentoIdentidad, fechaNacimiento, fechaAlta);

        Paciente nuevo = pacienteService.crearPaciente(nombres, apellidos, documentoIdentidad, fechaNacimiento, fechaAlta);

        log.info("✅ Paciente creado con ID: {}", nuevo != null ? nuevo.getId() : "null");
        return nuevo;
    }

    /**
     * Actualiza un paciente existente.
     */
    @MutationMapping
    public Paciente actualizarPaciente(@Argument String id,
                                       @Argument String nombres,
                                       @Argument String apellidos,
                                       @Argument String documentoIdentidad,
                                       @Argument String fechaNacimiento,
                                       @Argument String fechaAlta) {
        log.info("♻️ Mutation actualizarPaciente(id={}, nombres={}, apellidos={}, documentoIdentidad={}, fechaNacimiento={}, fechaAlta={})",
                id, nombres, apellidos, documentoIdentidad, fechaNacimiento, fechaAlta);

        Paciente actualizado = pacienteService.actualizarPaciente(id, nombres, apellidos, documentoIdentidad, fechaNacimiento, fechaAlta);

        log.info("✅ Resultado actualización: {}", actualizado != null ? actualizado.getId() : "null");
        return actualizado;
    }

    /**
     * Elimina un paciente por ID.
     */
    @MutationMapping
    public Boolean eliminarPaciente(@Argument String id) {
        log.info("🗑️ Mutation eliminarPaciente(id={})", id);
        try {
            pacienteService.eliminarPaciente(id);
            log.info("✅ Paciente eliminado correctamente");
            return true;
        } catch (Exception e) {
            log.error("❌ Error eliminando paciente: {}", e.getMessage(), e);
            return false;
        }
    }
}
