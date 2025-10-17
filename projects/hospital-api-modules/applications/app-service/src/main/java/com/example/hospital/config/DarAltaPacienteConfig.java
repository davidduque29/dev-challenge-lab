package com.example.hospital.config;

import com.example.hospital.ports.input.EventPublisherPort;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import com.example.hospital.usecase.paciente.DarAltaPacienteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ⚙️ Configuración del caso de uso DarAltaPacienteUseCase.
 * Define cómo se ensamblan los puertos (interfaces) con sus adaptadores.
 */
@Configuration
public class DarAltaPacienteConfig {

    @Bean
    public DarAltaPacienteUseCase darAltaPacienteUseCase(
            PacienteRepositoryPort pacienteRepositoryPort,
            CamillaRepositoryPort camillaRepositoryPort,
            EventPublisherPort eventPublisherPort
    ) {
        return new DarAltaPacienteUseCase(
                pacienteRepositoryPort,
                camillaRepositoryPort,
                eventPublisherPort
        );
    }
}
