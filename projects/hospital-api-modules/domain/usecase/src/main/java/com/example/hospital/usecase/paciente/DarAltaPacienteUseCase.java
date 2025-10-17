package com.example.hospital.usecase.paciente;

import com.example.hospital.document.PacienteDocument;
import com.example.hospital.exception.BusinessException;
import com.example.hospital.exception.MongoConnectionException;
import com.example.hospital.model.CamillaLiberadaEvent;
import com.example.hospital.ports.input.EventPublisherPort;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Caso de uso: Dar de alta a un paciente.
 * 1️⃣ Busca el paciente.
 * 2️⃣ Valida que esté en un estado válido para alta.
 * 3️⃣ Cambia su estado a "Alta".
 * 4️⃣ Actualiza la fecha.
 * 5️⃣ Libera la camilla asociada (cambia a "Disponible").
 * 6️⃣ Publica el evento CamillaLiberadaEvent en RabbitMQ.
 * 7️⃣ El listener lo recibe y lo confirma.
 */
@Slf4j
@RequiredArgsConstructor
public class DarAltaPacienteUseCase {

    private final PacienteRepositoryPort pacienteRepository;
    private final CamillaRepositoryPort camillaRepository;
    private final EventPublisherPort eventPublisher;

    /**
     * Método principal del caso de uso.
     * Ejecuta completo el flujo de alta de un paciente.
     */
    public PacienteDocument darAlta(String idPaciente) {
        log.info("🚪 Iniciando proceso de alta para paciente ID: {}", idPaciente);

        PacienteDocument paciente = obtenerPaciente(idPaciente);
        validarEstadoPaciente(paciente);
        actualizarEstadoAlta(paciente);
        liberarCamillaYPublicarEvento(paciente);

        log.info("🏁 Proceso de alta completado para paciente {}", paciente.getId());
        return paciente;
    }

    /**
     * Obtiene el paciente desde el repositorio y valida su existencia.
     */
    private PacienteDocument obtenerPaciente(String idPaciente) {
        try {
            return pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException(
                            "NOT_FOUND",
                            "Paciente no encontrado con ID: " + idPaciente,
                            "404"
                    ));
        } catch (Exception e) {
            log.error("❌ Error al consultar paciente con ID {}: {}", idPaciente, e.getMessage(), e);
            throw new MongoConnectionException("Error al conectar con la base de datos para buscar paciente " + idPaciente, e);
        }
    }

    /**
     * Valida que el paciente esté en un estado que permita el alta.
     */
    private void validarEstadoPaciente(PacienteDocument paciente) {
        if ("Alta".equalsIgnoreCase(paciente.getEstado())) {
            throw new BusinessException(
                    "CONFLICT",
                    "El paciente ya fue dado de alta anteriormente (fecha: " + paciente.getFechaAlta() + ")",
                    "409"
            );
        }

        if (!"Hospitalizado".equalsIgnoreCase(paciente.getEstado())) {
            throw new BusinessException(
                    "400",
                    "El paciente no puede darse de alta desde el estado: " + paciente.getEstado(),
                    "Estado inválido para alta médica"
            );
        }
    }


    /**
     * Actualiza el estado del paciente a "Alta" y registra la fecha de alta.
     */
    /**
     * Actualiza el estado del paciente a "Alta" y registra la fecha de alta.
     */
    private void actualizarEstadoAlta(PacienteDocument paciente) {
        LocalDateTime ahora = LocalDateTime.now();
        paciente.setEstado("Alta");
        paciente.setFechaAlta(ahora.toString());

        try {
            pacienteRepository.save(paciente);
            log.info("✅ Paciente {} dado de alta correctamente.", paciente.getPrimerNombre());
        } catch (Exception e) {
            log.error("❌ Error al guardar estado de alta del paciente {}: {}", paciente.getId(), e.getMessage(), e);
            throw new MongoConnectionException("Error al guardar alta del paciente " + paciente.getId(), e);
        }
    }


    /**
     * Libera la camilla asociada y publica el evento de liberación en RabbitMQ.
     */
    private void liberarCamillaYPublicarEvento(PacienteDocument paciente) {
        camillaRepository.findByPacienteId(paciente.getId()).ifPresentOrElse(camilla -> {
            if (!"Ocupada".equalsIgnoreCase(camilla.getEstado())) {
                log.warn("⚠️ Camilla {} no está ocupada, no se enviará evento de liberación.", camilla.getId());
                return;
            }

            // Actualiza el estado de la camilla
            camilla.setEstado("Disponible");
            try {
                camillaRepository.save(camilla);
                log.info("Camilla {} liberada exitosamente.", camilla.getId());
            } catch (Exception e) {
                log.error("❌ Error al guardar estado de la camilla {}: {}", camilla.getId(), e.getMessage(), e);
                throw new MongoConnectionException("Error al liberar camilla " + camilla.getId(), e);
            }

            // Publica el evento RabbitMQ
            CamillaLiberadaEvent evento = new CamillaLiberadaEvent(
                    camilla.getId(),
                    paciente.getId(),
                    paciente.getFechaAlta(),
                    "system_auto"
            );

            try {
                eventPublisher.publish("hospital.camilla.disponible", evento);
                log.info("📤 Evento publicado correctamente para camilla {}", camilla.getId());
            } catch (Exception e) {
                log.error("⚠️ Error al publicar evento RabbitMQ para camilla {}: {}", camilla.getId(), e.getMessage(), e);
                throw new BusinessException(
                        "INTERNAL_ERROR",
                        "Fallo al publicar evento de liberación de camilla",
                        "500"
                );
            }
        }, () -> log.warn("⚠️ No se encontró camilla asociada al paciente {}", paciente.getId()));
    }
}
