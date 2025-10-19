package graphql.mutation;

import com.example.hospital.document.CamillaDocument;
import com.example.hospital.usecase.camilla.CamillaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * 🎯 Resolver GraphQL para Mutations relacionadas con Camillas.
 * Gestiona la asignación, creación y liberación de camillas.
 */
@Controller
public class CamillaMutationResolver {

    private static final Logger log = LoggerFactory.getLogger(CamillaMutationResolver.class);
    private final CamillaUseCase camillaService;

    public CamillaMutationResolver(CamillaUseCase camillaService) {
        this.camillaService = camillaService;
    }

    // =============================
    // CREAR CAMILLA
    // =============================

    /**
     * Crea una nueva camilla en el sistema hospitalario.
     * Ejemplo de uso (GraphQL):
     * mutation {
     * crearCamilla(estado: "Disponible", habitacion: "201-B") {
     * id
     * estado
     * habitacion
     * }
     * }
     */
    @MutationMapping
    public CamillaDocument crearCamilla(
            @Argument String estado,
            @Argument String habitacion
    ) {
        log.info("🛠️ [GraphQL] Mutation → crearCamilla()");
        CamillaDocument nueva = new CamillaDocument();
        nueva.setEstado(estado != null ? estado : "Disponible");
        nueva.setHabitacion(habitacion);
        return camillaService.crearCamilla(nueva);
    }

    // =============================
    //  ASIGNAR PACIENTE
    // =============================

    /**
     * Asigna una camilla a un paciente hospitalizado.
     * Ejemplo (GraphQL):
     * mutation {
     * asignarPaciente(idCamilla: "68e84d03c693de24f9824a61", idPaciente: "68e847552d4447a1dc59fb76") {
     * id
     * estado
     * habitacion
     * paciente {
     * primerNombre
     * primerApellido
     * estado
     * }
     * }
     * }
     */
    @MutationMapping
    public CamillaDocument asignarPaciente(
            @Argument String idCamilla,
            @Argument String idPaciente
    ) {
        log.info("🩺 [GraphQL] Mutation → asignarPaciente(camilla={}, paciente={})", idCamilla, idPaciente);
        return camillaService.asignarPaciente(idCamilla, idPaciente);
    }

    // =============================
    // LIBERAR CAMILLA
    // =============================

    /**
     * Libera una camilla cuando un paciente es dado de alta.
     * Ejemplo (GraphQL):
     * mutation {
     * liberarCamilla(idCamilla: "68e84d03c693de24f9824a61", fechaFin: "2025-10-10") {
     * id
     * estado
     * habitacion
     * fechaFin
     * }
     * }
     */
    @MutationMapping
    public CamillaDocument liberarCamilla(
            @Argument String idCamilla,
            @Argument String fechaFin
    ) {
        log.info("🚪 [GraphQL] Mutation → liberarCamilla(camilla={}, fechaFin={})", idCamilla, fechaFin);
        return camillaService.liberarCamilla(idCamilla, fechaFin);
    }

    // =============================
    // 🗑️ ELIMINAR CAMILLA
    // =============================

    /**
     * Elimina una camilla del sistema (solo si no está ocupada).
     * Ejemplo (GraphQL):
     * mutation {
     * eliminarCamilla(id: "68e84d03c693de24f9824a61")
     * }
     */
    @MutationMapping
    public String eliminarCamilla(@Argument String id) {
        log.info("🗑️ [GraphQL] Mutation → eliminarCamilla() ID: {}", id);
        camillaService.eliminarCamilla(id);
        return "✅ CamillaDocument eliminada exitosamente con ID: " + id;
    }
}
