package com.example.hospitalapi.events.producer;


import com.example.hospitalapi.events.config.RabbitMQConfig;
import com.example.hospitalapi.events.model.CamillaLiberadaEvent;
import com.example.hospitalapi.repository.CamillaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 🎯 EventListener
 * Escucha los mensajes enviados a la cola hospital.events.queue.
 * Cada mensaje recibido representa un evento publicado por algún productor (ej: alta de paciente).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventListener {

    // 🧩 Repositorio que accede a la colección de camillas en MongoDB
    private final CamillaRepository camillaRepository;

    /**
     * ✅ Método que escucha automáticamente los mensajes del exchange.
     *
     * @param event Evento deserializado desde JSON a un objeto Java (CamillaLiberadaEvent)
     *
     * 'containerFactory = "rabbitListenerContainerFactory"'
     * -> le indica a Spring que debe usar el convertidor Jackson2JsonMessageConverter
     *    para transformar el JSON recibido en un objeto CamillaLiberadaEvent.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME,
            containerFactory = "rabbitListenerContainerFactory")
    public void handleCamillaLiberada(CamillaLiberadaEvent event) {
        log.info("📩 Evento recibido: {}", event);

        try {
            // Buscar la camilla en la base de datos
            camillaRepository.findById(event.getCamillaId())
                    .ifPresentOrElse(camilla -> {
                        // Actualizar su estado a Disponible y limpiar datos del paciente
                        camilla.setEstado("Disponible");
                        camilla.setFechaFin(event.getFechaLiberacion());
                        camilla.setPaciente(null); // 🔥 rompe la referencia con el paciente
                        camillaRepository.save(camilla);

                        log.info("🛏️ Camilla '{}' liberada por '{}', fecha {}",
                                event.getCamillaId(),
                                event.getOrigen(),
                                event.getFechaLiberacion());
                        log.info("✅ Camilla actualizada correctamente en base de datos.");
                    }, () -> {
                        log.warn("⚠️ No se encontró la camilla con ID '{}'.", event.getCamillaId());
                    });

        } catch (Exception e) {
            log.error("❌ Error procesando evento: {}", e.getMessage(), e);
        }
    }
}

