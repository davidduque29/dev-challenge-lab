package com.example.hospitalapi.graphql.mutation;

import com.example.hospitalapi.model.Camilla;
import com.example.hospitalapi.model.Paciente;
import com.example.hospitalapi.service.CamillaService;
import com.example.hospitalapi.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

/**
 * Resolver GraphQL para mutaciones de Camilla (crear, actualizar, asignar, liberar, eliminar).
 */
@Controller
public class CamillaMutationResolver {

    private static final Logger log = LoggerFactory.getLogger(CamillaMutationResolver.class);

    private final CamillaService camillaService;
    private final PacienteRepository pacienteRepository;

    public CamillaMutationResolver(CamillaService camillaService, PacienteRepository pacienteRepository) {
        this.camillaService = camillaService;
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Crea una nueva camilla.
     */
    @MutationMapping
    public Camilla crearCamilla(@Argument String estado,
                                @Argument String habitacion,
                                @Argument String pacienteId,
                                @Argument String fechaInicio,
                                @Argument String fechaFin) {
        log.info("🛏️ Mutation crearCamilla(estado={}, habitacion={}, pacienteId={})",
                estado, habitacion, pacienteId);

        try {
            Camilla nueva = new Camilla();
            nueva.setEstado(estado);
            nueva.setHabitacion(habitacion);
            nueva.setFechaInicio(fechaInicio);
            nueva.setFechaFin(fechaFin);

            if (pacienteId != null) {
                Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
                paciente.ifPresent(nueva::setPaciente);
            }

            Camilla creada = camillaService.crearCamilla(nueva);
            log.info("✅ Camilla creada con ID: {}", creada.getId());
            return creada;

        } catch (Exception e) {
            log.error("❌ Error en crearCamilla: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Actualiza una camilla existente.
     */
    @MutationMapping
    public Camilla actualizarCamilla(@Argument String id,
                                     @Argument String estado,
                                     @Argument String habitacion,
                                     @Argument String pacienteId,
                                     @Argument String fechaInicio,
                                     @Argument String fechaFin) {
        log.info("♻️ Mutation actualizarCamilla(id={}, estado={}, habitacion={}, pacienteId={})",
                id, estado, habitacion, pacienteId);

        try {
            Camilla actualizada = new Camilla();
            actualizada.setEstado(estado);
            actualizada.setHabitacion(habitacion);
            actualizada.setFechaInicio(fechaInicio);
            actualizada.setFechaFin(fechaFin);

            if (pacienteId != null) {
                pacienteRepository.findById(pacienteId)
                        .ifPresent(actualizada::setPaciente);
            }

            Camilla resultado = camillaService.actualizarCamilla(id, actualizada);
            log.info("✅ Camilla actualizada correctamente: {}", resultado.getId());
            return resultado;

        } catch (Exception e) {
            log.error("❌ Error en actualizarCamilla: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Asigna un paciente a una camilla.
     */
    @MutationMapping
    public Camilla asignarPaciente(@Argument String idCamilla,
                                   @Argument String idPaciente) {
        log.info("🩺 Mutation asignarPaciente(idCamilla={}, idPaciente={})", idCamilla, idPaciente);

        try {
            Camilla camilla = camillaService.asignarPaciente(idCamilla, idPaciente);
            log.info("✅ Paciente {} asignado a camilla {}", idPaciente, idCamilla);
            return camilla;
        } catch (Exception e) {
            log.error("❌ Error en asignarPaciente: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Libera una camilla (cambia estado a 'Disponible' y elimina paciente).
     */
    @MutationMapping
    public Camilla liberarCamilla(@Argument String idCamilla,
                                  @Argument String fechaFin) {
        log.info("🚪 Mutation liberarCamilla(idCamilla={}, fechaFin={})", idCamilla, fechaFin);

        try {
            Camilla camilla = camillaService.liberarCamilla(idCamilla, fechaFin);
            log.info("✅ Camilla liberada correctamente con ID {}", idCamilla);
            return camilla;
        } catch (Exception e) {
            log.error("❌ Error en liberarCamilla: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Elimina una camilla por ID.
     */
    @MutationMapping
    public Boolean eliminarCamilla(@Argument String id) {
        log.info("🗑️ Mutation eliminarCamilla(id={})", id);

        try {
            camillaService.eliminarCamilla(id);
            log.info("✅ Camilla eliminada correctamente con ID {}", id);
            return true;
        } catch (Exception e) {
            log.error("❌ Error eliminando camilla con ID {}: {}", id, e.getMessage(), e);
            return false;
        }
    }
}
