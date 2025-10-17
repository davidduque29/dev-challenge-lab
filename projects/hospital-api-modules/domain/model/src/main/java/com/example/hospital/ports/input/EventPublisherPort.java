package com.example.hospital.ports.input;


import com.example.hospital.model.CamillaLiberadaEvent;

public interface EventPublisherPort {
    void publish(String routingKey, CamillaLiberadaEvent event);
}