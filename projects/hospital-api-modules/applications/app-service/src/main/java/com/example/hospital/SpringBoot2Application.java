package com.example.hospital;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.hospital", "graphql"})
public class SpringBoot2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2Application.class, args);
        System.out.println("✅ Aplicación iniciada en http://localhost:8080");
    }
}

