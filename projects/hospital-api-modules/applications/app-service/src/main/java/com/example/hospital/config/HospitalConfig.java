package com.example.hospital.config;

import com.example.hospital.usecase.hospital.HospitalUseCase;
import com.example.hospital.ports.out.HospitalRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HospitalConfig {
    @Bean
    public HospitalUseCase hospitalUseCase(HospitalRepositoryPort hospitalRepositoryPort) {
        return new HospitalUseCase(hospitalRepositoryPort);
    }

}
