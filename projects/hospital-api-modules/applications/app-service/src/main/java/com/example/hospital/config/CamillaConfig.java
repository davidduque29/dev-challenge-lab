package com.example.hospital.config;


import com.example.hospital.ports.out.CamillaRepositoryPort;
import com.example.hospital.ports.out.PacienteRepositoryPort;
import com.example.hospital.usecase.camilla.CamillaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class CamillaConfig {
    @Primary
    @Bean
    public CamillaUseCase camillaUseCase(CamillaRepositoryPort camillaRepositoryPort, PacienteRepositoryPort pacienteRepository) {
        return new CamillaUseCase(camillaRepositoryPort, pacienteRepository);
    }
}