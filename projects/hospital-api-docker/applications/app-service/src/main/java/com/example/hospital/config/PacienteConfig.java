package com.example.hospital.config;


import com.example.hospital.ports.out.PacienteRepositoryPort;
import com.example.hospital.usecase.paciente.PacienteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PacienteConfig {
    @Primary
    @Bean
    public PacienteUseCase pacienteUseCase(PacienteRepositoryPort pacienteRepositoryPort) {
        return new PacienteUseCase(pacienteRepositoryPort);
    }

}
