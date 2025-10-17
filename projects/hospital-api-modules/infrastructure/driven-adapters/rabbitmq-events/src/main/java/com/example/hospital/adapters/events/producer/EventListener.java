package com.example.hospital.adapters.events.producer;

import com.example.hospital.adapters.events.config.RabbitMQConfig;
import com.example.hospital.usecase.camilla.EventCamillaUseCase;
import com.example.hospital.model.CamillaLiberadaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 🧩 Listener que actúa como punto de entrada (adapter) para eventos de RabbitMQ.
 * <p>
 * Su única responsabilidad es recibir eventos y delegarlos al caso de uso.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventListener {

    private final EventCamillaUseCase eventCamillaUseCase;

    /**
     * Escucha los mensajes enviados a la cola hospital.events.queue.
     * Cada mensaje recibido representa un evento CamillaLiberadaEvent.
     */
    @RabbitListener(
            queues = RabbitMQConfig.QUEUE_NAME,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleCamillaLiberada(CamillaLiberadaEvent event) {
        log.info("📥 Evento recibido: {}", event);

        try {
            eventCamillaUseCase.procesarCamillaLiberada(
                    event.getCamillaId(),
                    event.getFechaLiberacion(),
                    event.getOrigen()
            );
        } catch (Exception e) {
            log.error("❌ Error procesando evento: {}", e.getMessage(), e);
        }
    }
}
