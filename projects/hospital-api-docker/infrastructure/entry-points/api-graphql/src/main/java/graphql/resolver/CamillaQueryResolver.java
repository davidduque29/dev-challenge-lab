package graphql.resolver;


import com.example.hospital.model.Camilla;
import com.example.hospital.usecase.camilla.CamillaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * Resolver GraphQL para consultas de camillas (lecturas).
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class CamillaQueryResolver {

    private final CamillaUseCase camillaUseCase;

    /**
     * Retorna todas las camillas con estado "Disponible".
     */
    @QueryMapping
    public List<Camilla> camillasDisponibles() {
        log.info("📥 Query GraphQL: camillasDisponibles()");
        List<Camilla> disponibles = camillaUseCase.obtenerCamillasDisponibles();
        log.info("📤 Total camillas disponibles encontradas: {}", disponibles.size());
        return disponibles;
    }

    /**
     * Retorna una camilla específica por su ID.
     */
    @QueryMapping
    public Camilla camillaPorId(@Argument String id) {
        log.info("📥 Query GraphQL: camillaPorId(id={})", id);
        Optional<Camilla> camilla = camillaUseCase.obtenerCamillaPorId(id);

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
        List<Camilla> todas = camillaUseCase.obtenerTodasLasCamillas();
        log.info("📤 Total camillas encontradas: {}", todas.size());
        return todas;
    }
}