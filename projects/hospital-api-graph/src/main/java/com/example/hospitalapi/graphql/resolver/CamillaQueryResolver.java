package com.example.hospitalapi.graphql.resolver;

import com.example.hospitalapi.model.Camilla;
import com.example.hospitalapi.service.CamillaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * Resolver GraphQL para consultas de camillas (lecturas).
 */
@Controller
public class CamillaQueryResolver {

    private static final Logger log = LoggerFactory.getLogger(CamillaQueryResolver.class);
    private final CamillaService camillaService;

    public CamillaQueryResolver(CamillaService camillaService) {
        this.camillaService = camillaService;
    }

    /**
     * Retorna todas las camillas con estado "Disponible".
     */
    @QueryMapping
    public List<Camilla> camillasDisponibles() {
        log.info("📥 Query GraphQL: camillasDisponibles()");
        List<Camilla> disponibles = camillaService.obtenerCamillasDisponibles();
        log.info("📤 Total camillas disponibles encontradas: {}", disponibles.size());
        return disponibles;
    }

    /**
     * Retorna una camilla específica por su ID.
     * El nombre del argumento debe coincidir con el schema (id: ID!)
     */
    @QueryMapping
    public Camilla camillaPorId(@Argument("id") String id) {
        log.info("📥 Query GraphQL: camillaPorId(id={})", id);
        Optional<Camilla> camilla = camillaService.obtenerCamillaPorId(id);

        if (camilla.isPresent()) {
            log.info("✅ Camilla encontrada: {}", camilla.get().getHabitacion());
            return camilla.get();
        } else {
            log.warn("⚠️ No se encontró camilla con ID {}", id);
            return null;
        }
    }

    /**
     * Retorna todas las camillas sin importar su estado.
     */
    @QueryMapping
    public List<Camilla> todasLasCamillas() {
        log.info("📥 Query GraphQL: todasLasCamillas()");
        List<Camilla> todas = camillaService.obtenerTodasLasCamillas();
        log.info("📤 Total camillas encontradas: {}", todas.size());
        return todas;
    }
}
