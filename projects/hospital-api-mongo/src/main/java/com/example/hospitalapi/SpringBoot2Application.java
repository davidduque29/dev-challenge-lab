package com.example.hospitalapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2Application.class, args);
        System.out.println("✅ Aplicación iniciada en http://localhost:8080");
    }
}

