package com.example.hospitalapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    private final Environment env;

    @Value("${spring.data.mongodb.uri:}")
    private String mongoUri;  // el ':' indica "valor por defecto vacío" si no existe

    public StartupLogger(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        try {
            String[] activeProfiles = env.getActiveProfiles();

            if (activeProfiles.length == 0) {
                System.out.println("⚠️  No hay perfil activo. Usando configuración por defecto.");
            } else {
                for (String profile : activeProfiles) {
                    switch (profile.toLowerCase()) {
                        case "local":
                            System.out.println("💻 Perfil activo: LOCAL");
                            System.out.println("➡️  Conectado a base local → " + mongoUri);
                            break;
                        case "cloud":
                            System.out.println("☁️ Perfil activo: CLOUD (MongoDB Atlas)");
                            System.out.println("➡️  Conectado a base en la nube → " + mongoUri);
                            break;
                        default:
                            System.out.println("🧩 Perfil activo: " + profile);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo obtener el URI de Mongo todavía. El perfil se cargará después.");
        }
    }
}
