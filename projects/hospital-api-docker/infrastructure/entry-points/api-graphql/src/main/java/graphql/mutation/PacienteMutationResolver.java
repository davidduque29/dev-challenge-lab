package graphql.mutation;

 import com.example.hospital.model.Paciente;
 import com.example.hospital.usecase.paciente.PacienteUseCase;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


/**
 * 🎯 Resolver GraphQL para Mutations relacionadas con Pacientes.
 * Gestiona la creación, actualización y eliminación de pacientes.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PacienteMutationResolver {

    private final PacienteUseCase pacienteUseCase;

    // =============================
    // 🏥 CREAR PACIENTE
    // =============================

    /**
     * Crea un nuevo paciente en el sistema hospitalario.
     * Ejemplo (GraphQL):
     * mutation {
     *   crearPaciente(
     *     primerNombre: "Juan",
     *     primerApellido: "Pérez",
     *     documentoIdentidad: "12345",
     *     genero: "M",
     *     tipoSangre: "O+"
     *   ) {
     *     id
     *     primerNombre
     *     primerApellido
     *     genero
     *     tipoSangre
     *   }
     * }
     */
    @MutationMapping
    public Paciente crearPaciente(
            @Argument String primerNombre,
            @Argument String segundoNombre,
            @Argument String primerApellido,
            @Argument String segundoApellido,
            @Argument String documentoIdentidad,
            @Argument String genero,
            @Argument String tipoSangre,
            @Argument String alergias,
            @Argument String eps
    ) {
        log.info("🧬 [GraphQL] Mutation → crearPaciente()");
        Paciente nuevo = new Paciente();
        nuevo.setPrimerNombre(primerNombre);
        nuevo.setSegundoNombre(segundoNombre);
        nuevo.setPrimerApellido(primerApellido);
        nuevo.setSegundoApellido(segundoApellido);
        nuevo.setDocumentoIdentidad(documentoIdentidad);
        nuevo.setGenero(genero);
        nuevo.setTipoSangre(tipoSangre);
        nuevo.setAlergias(alergias);
        nuevo.setEps(eps);
        nuevo.setEstado("Hospitalizado");

        Paciente creado = pacienteUseCase.crearPaciente(nuevo);
        log.info("✅ Paciente creado correctamente: {} {}", primerNombre, primerApellido);
        return creado;
    }

    // =============================
    // 🧩 ACTUALIZAR PACIENTE
    // =============================

    /**
     * Actualiza la información de un paciente existente.
     * Ejemplo (GraphQL):
     * mutation {
     *   actualizarPaciente(
     *     id: "68e847552d4447a1dc59fb76",
     *     alergias: "Ninguna",
     *     eps: "Nueva EPS"
     *   ) {
     *     id
     *     primerNombre
     *     alergias
     *     eps
     *   }
     * }
     */
    @MutationMapping
    public Paciente actualizarPaciente(
            @Argument String id,
            @Argument String alergias,
            @Argument String eps,
            @Argument String estado
    ) {
        log.info("🧩 [GraphQL] Mutation → actualizarPaciente(id={})", id);
        Paciente actualizado = pacienteUseCase.actualizarPaciente(id, alergias, eps, estado);
        log.info("✅ Paciente actualizado correctamente: {}", id);
        return actualizado;
    }

    // =============================
    // 🗑️ ELIMINAR PACIENTE
    // =============================

    /**
     * Elimina un paciente del sistema.
     * Ejemplo (GraphQL):
     * mutation {
     *   eliminarPaciente(id: "68e847552d4447a1dc59fb76")
     * }
     */
    @MutationMapping
    public String eliminarPaciente(@Argument String id) {
        log.info("🗑️ [GraphQL] Mutation → eliminarPaciente(id={})", id);
        pacienteUseCase.eliminarPaciente(id);
        log.info("✅ Paciente eliminado correctamente: {}", id);
        return "✅ Paciente eliminado exitosamente con ID: " + id;
    }
}
