package com.example.hospital.adapters.events;

import com.example.hospital.model.CamillaLiberadaEvent;
import com.example.hospital.ports.input.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitEventPublisherAdapter implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String routingKey, CamillaLiberadaEvent event) {
        rabbitTemplate.convertAndSend("hospital.events.exchange", routingKey, event);
    }
}
