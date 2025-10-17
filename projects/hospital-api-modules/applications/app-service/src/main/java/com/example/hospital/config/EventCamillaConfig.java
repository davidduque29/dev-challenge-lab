package com.example.hospital.config;

import com.example.hospital.usecase.camilla.EventCamillaUseCase;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EventCamillaConfig {
    @Primary
    @Bean
    public EventCamillaUseCase eventCamillaUseCase(CamillaRepositoryPort camillaRepositoryPort) {
        return new EventCamillaUseCase(camillaRepositoryPort);
    }

}
