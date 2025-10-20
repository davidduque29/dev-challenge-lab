package graphql.mutation;

import com.example.hospital.model.Camilla;
import com.example.hospital.usecase.camilla.CamillaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * 🎯 Resolver GraphQL para Mutations relacionadas con Camillas.
 * Gestiona la asignación, creación y liberación de camillas.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class CamillaMutationResolver {

    private final CamillaUseCase camillaUseCase;

    /**
     * 🛠️ CREAR CAMILLA =============================
     * Crea una nueva camilla en el sistema hospitalario.
     * Ejemplo de uso (GraphQL):
     * mutation {
     *   crearCamilla(estado: "Disponible", habitacion: "201-B") {
     *     id
     *     estado
     *     habitacion
     *   }
     * }
     */
    @MutationMapping
    public Camilla crearCamilla(
            @Argument String estado,
            @Argument String habitacion
    ) {
        log.info("🛠️ [GraphQL] Mutation → crearCamilla()");
        Camilla nueva = new Camilla();
        nueva.setEstado(estado != null ? estado : "Disponible");
        nueva.setHabitacion(habitacion);
        Camilla creada = camillaUseCase.crearCamilla(nueva);
        log.info("✅ Camilla creada: {} ({})", creada.getHabitacion(), creada.getEstado());
        return creada;
    }

    /**
     * ‍♂️ ASIGNAR PACIENTE =============================
     * Asigna una camilla a un paciente hospitalizado.
     * Ejemplo (GraphQL):
     * mutation {
     *   asignarPaciente(idCamilla: "68e84d03...", idPaciente: "68e8475...") {
     *     id
     *     estado
     *     habitacion
     *     paciente {
     *       primerNombre
     *       primerApellido
     *       estado
     *     }
     *   }
     * }
     */
    @MutationMapping
    public Camilla asignarPaciente(
            @Argument String idCamilla,
            @Argument String idPaciente
    ) {
        log.info("🩺 [GraphQL] Mutation → asignarPaciente(camilla={}, paciente={})", idCamilla, idPaciente);
        Camilla actualizada = camillaUseCase.asignarPaciente(idCamilla, idPaciente);
        log.info("✅ Paciente asignado correctamente a camilla {}", idCamilla);
        return actualizada;
    }

    /**
     *  LIBERAR CAMILLA =============================
     * Libera una camilla cuando un paciente es dado de alta.
     * Ejemplo (GraphQL):
     * mutation {
     *   liberarCamilla(idCamilla: "68e84d03...", fechaFin: "2025-10-10") {
     *     id
     *     estado
     *     habitacion
     *     fechaFin
     *   }
     * }
     */
    @MutationMapping
    public Camilla liberarCamilla(
            @Argument String idCamilla,
            @Argument String fechaFin
    ) {
        log.info("🚪 [GraphQL] Mutation → liberarCamilla(camilla={}, fechaFin={})", idCamilla, fechaFin);
        Camilla liberada = camillaUseCase.liberarCamilla(idCamilla, fechaFin);
        log.info("✅ Camilla liberada correctamente: {}", idCamilla);
        return liberada;
    }

    /**
     *  🗑️ ELIMINAR CAMILLA =============================
     * Elimina una camilla del sistema (solo si no está ocupada).
     * Ejemplo (GraphQL):
     * mutation {
     *   eliminarCamilla(id: "68e84d03...")
     * }
     */
    @MutationMapping
    public String eliminarCamilla(@Argument String id) {
        log.info("🗑️ [GraphQL] Mutation → eliminarCamilla() ID: {}", id);
        camillaUseCase.eliminarCamilla(id);
        log.info("✅ Camilla eliminada exitosamente con ID: {}", id);
        return "✅ Camilla eliminada exitosamente con ID: " + id;
    }
}