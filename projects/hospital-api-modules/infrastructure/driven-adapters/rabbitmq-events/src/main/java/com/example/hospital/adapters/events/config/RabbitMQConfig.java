package com.example.hospital.adapters.events.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;

/**
 * ⚙️ Configuración central de RabbitMQ para el microservicio hospital-api-events.
 *
 * Aquí se definen:
 *  - La cola donde se almacenarán los mensajes.
 *  - El exchange (intercambiador) que recibe los mensajes del sistema.
 *  - El binding (regla) que une la cola y el exchange.
 *  - El convertidor de mensajes (para transformar objetos Java a JSON y viceversa).
 */
@Configuration
public class RabbitMQConfig {

    //  Nombre de la cola donde se recibirán los mensajes.
    public static final String QUEUE_NAME = "hospital.events.queue";

    //  Nombre del exchange que actúa como punto de distribución de los mensajes.
    public static final String EXCHANGE_NAME = "hospital.events.exchange";

    // 🔑 Clave de enrutamiento base (no estrictamente necesaria si usamos patrón "hospital.#")
    public static final String ROUTING_KEY = "hospital.events.key";

    /**
     * 1 -  Definición de la cola (Queue)
     *
     * - El segundo parámetro 'true' indica que la cola es "durable",
     *   es decir, sobrevivirá a reinicios del broker RabbitMQ.
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    /**
     * 2  -  Definición del exchange
     *
     * - Tipo "TopicExchange": permite usar patrones flexibles con comodines
     *   ('*' para una palabra, '#' para varias).
     * - Es ideal para arquitecturas event-driven con diferentes tipos de eventos.
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * 3 - Creación del binding (enlace entre exchange y cola)
     *
     * - "hospital.#" significa que cualquier mensaje cuya routing key empiece con "hospital."
     *   será dirigido a esta cola.
     * - Ejemplo: hospital.camilla.disponible, hospital.paciente.creado, etc.
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("hospital.#"); // patrón flexible para todos los eventos del dominio hospitalario
    }

    /**
     *  4️ - Convertidor de mensajes JSON
     *
     * - Spring usará Jackson para convertir objetos Java (CamillaLiberadaEvent, etc.)
     *   a JSON al enviar, y viceversa al recibir.
     * - Evita que los mensajes lleguen como "[B@36a63ba" (byte[]).
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 5 - Configuración del RabbitTemplate (cliente de envío)
     *
     * - Inyecta el convertidor JSON en el template, para que todos los envíos
     *   de eventos se serialicen automáticamente.
     * - Este template es el que usa tu clase EventPublisher.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter()); // 🔥 importante
        return factory;
    }
}
