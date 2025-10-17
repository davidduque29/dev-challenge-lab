package com.example.hospitalapi.graphql.mutation;

import com.example.hospitalapi.document.PacienteDocument;
import com.example.hospitalapi.service.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * 🎯 Resolver GraphQL para manejar las Mutations relacionadas con pacientes:
 * - Crear nuevo paciente
 * - Actualizar paciente existente
 * - Eliminar paciente
 */
@Controller
public class PacienteMutationResolver {

    private static final Logger log = LoggerFactory.getLogger(PacienteMutationResolver.class);
    private final PacienteService pacienteService;

    public PacienteMutationResolver(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * 🧍‍♂️ Crear un nuevo paciente en la base de datos (MongoDB).
     */
    @MutationMapping
    public PacienteDocument crearPaciente(
            @Argument String primerNombre,
            @Argument String segundoNombre,
            @Argument String primerApellido,
            @Argument String segundoApellido,
            @Argument String documentoIdentidad,
            @Argument String fechaNacimiento,
            @Argument String tipoSangre,
            @Argument String genero,
            @Argument String alergias,
            @Argument String estado,
            @Argument String numeroHistoriaClinica,
            @Argument String eps
    ) {
        log.info("🩺 [GraphQL] Mutation → crearPaciente()");
        return pacienteService.crearPaciente(
                primerNombre, segundoNombre, primerApellido, segundoApellido,
                documentoIdentidad, fechaNacimiento, tipoSangre, genero,
                alergias, estado, numeroHistoriaClinica, eps
        );
    }

    /**
     * ♻️ Actualizar un paciente existente.
     */
    @MutationMapping
    public PacienteDocument actualizarPaciente(
            @Argument String id,
            @Argument String primerNombre,
            @Argument String segundoNombre,
            @Argument String primerApellido,
            @Argument String segundoApellido,
            @Argument String documentoIdentidad,
            @Argument String fechaNacimiento,
            @Argument String tipoSangre,
            @Argument String genero,
            @Argument String alergias,
            @Argument String estado,
            @Argument String fechaAlta,
            @Argument String numeroHistoriaClinica,
            @Argument String eps
    ) {
        log.info("♻️ [GraphQL] Mutation → actualizarPaciente() para ID: {}", id);
        return pacienteService.actualizarPaciente(
                id, primerNombre, segundoNombre, primerApellido, segundoApellido,
                documentoIdentidad, fechaNacimiento, tipoSangre, genero,
                alergias, estado, fechaAlta, numeroHistoriaClinica, eps
        );
    }

    /**
     * 🗑️ Eliminar un paciente por su ID.
     */
    @MutationMapping
    public String eliminarPaciente(@Argument String id) {
        log.info("🗑️ [GraphQL] Mutation → eliminarPaciente() ID: {}", id);
        pacienteService.eliminarPaciente(id);
        return "✅ Paciente eliminado exitosamente con ID: " + id;
    }
}
