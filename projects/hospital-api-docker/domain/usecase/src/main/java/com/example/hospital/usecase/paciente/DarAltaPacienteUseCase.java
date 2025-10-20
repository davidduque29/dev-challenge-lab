package com.example.hospital.usecase.paciente;


import com.example.hospital.exception.BusinessException;
import com.example.hospital.exception.MongoConnectionException;
import com.example.hospital.model.Camilla;
import com.example.hospital.model.CamillaLiberadaEvent;
import com.example.hospital.model.Paciente;
import com.example.hospital.ports.input.EventPublisherPort;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import com.example.hospital.usecase.paciente.result.AltaPacienteResult;
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
     * Ejecuta el flujo completo de alta y retorna tanto el paciente como la camilla liberada.
     */
    public AltaPacienteResult darAlta(String idPaciente) {
        log.info("🚪 Iniciando proceso de alta para paciente ID: {}", idPaciente);

        Paciente paciente = obtenerPaciente(idPaciente);
        validarEstadoPaciente(paciente);
        actualizarEstadoAlta(paciente);

        Camilla camillaLiberada = liberarCamillaYPublicarEvento(paciente);

        log.info("🏁 Proceso de alta completado para paciente {}", paciente.getId());
        return new AltaPacienteResult(paciente, camillaLiberada);
    }

    /**
     * Obtiene el paciente desde el repositorio y valida su existencia.
     */
    private Paciente obtenerPaciente(String idPaciente) {
        try {
            return pacienteRepository.findById(idPaciente)
                    .orElseThrow(() -> new BusinessException(
                            "NOT_FOUND",
                            "Paciente no encontrado con ID: " + idPaciente,
                            "404"
                    ));
        } catch (Exception e) {
            log.error("❌ Error al consultar paciente con ID {}: {}", idPaciente, e.getMessage(), e);
            throw new MongoConnectionException(
                    "Error al conectar con la base de datos para buscar paciente " + idPaciente, e
            );
        }
    }

    /**
     * Valida que el paciente esté en un estado que permita el alta.
     */
    private void validarEstadoPaciente(Paciente paciente) {
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
    private void actualizarEstadoAlta(Paciente paciente) {
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
    private Camilla liberarCamillaYPublicarEvento(Paciente paciente) {
        return camillaRepository.findByPacienteId(paciente.getId())
                .map(camilla -> {
                    if (!"Ocupada".equalsIgnoreCase(camilla.getEstado())) {
                        log.warn("⚠️ Camilla {} no está ocupada, no se liberará.", camilla.getId());
                        return null;
                    }

                    camilla.setEstado("Disponible");
                    camilla.setPaciente(null);
                    camilla.setFechaFin(paciente.getFechaAlta());

                    try {
                        camillaRepository.save(camilla);
                        log.info("🛏️ Camilla {} liberada exitosamente.", camilla.getId());
                    } catch (Exception e) {
                        log.error("❌ Error al guardar estado de la camilla {}: {}", camilla.getId(), e.getMessage(), e);
                        throw new MongoConnectionException("Error al liberar camilla " + camilla.getId(), e);
                    }

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

                    return camilla;
                })
                .orElse(null);
    }
}