package com.example.hospitalapi.events.consumer;


import com.example.hospitalapi.events.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 🔔 EventPublisher
 * Clase responsable de publicar eventos en RabbitMQ.
 * Envia mensajes al exchange definido en RabbitMQConfig.
 */
@Slf4j
@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publica un mensaje en RabbitMQ.
     * @param routingKey clave de enrutamiento (por ejemplo: hospital.camilla.disponible)
     * @param message cuerpo del mensaje (puede ser un objeto o JSON)
     */
    public void publish(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,  // Exchange configurado
                    routingKey,                    // Routing key
                    message                        // Mensaje que se enviará
            );
            log.info("✅ Evento publicado a exchange='{}' con routingKey='{}' -> {}",
                    RabbitMQConfig.EXCHANGE_NAME, routingKey, message);
        } catch (Exception e) {
            log.error("❌ Error publicando evento a RabbitMQ: {}", e.getMessage(), e);
        }
    }
}

