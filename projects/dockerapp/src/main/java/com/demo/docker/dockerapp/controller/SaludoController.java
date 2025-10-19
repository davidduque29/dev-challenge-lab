package com.demo.docker.dockerapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {

    @GetMapping("/saludo")
    public String saludo() {
        return "👋 Hola, Docker y Spring Boot están corriendo correctamente!";
    }
}
