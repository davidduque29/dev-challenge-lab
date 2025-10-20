package com.example.hospital.usecase.camilla;

import com.example.hospital.model.Camilla;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 🧩 Caso de uso para procesar eventos relacionados con Camillas.
 * Se ejecuta ante eventos externos (por ejemplo, liberación de camillas).
 */
@Slf4j
@RequiredArgsConstructor
public class EventCamillaUseCase {

    private final CamillaRepositoryPort camillaRepositoryPort;

    /**
     * Procesa un evento de liberación de camilla, marcándola como disponible.
     */
    public void procesarCamillaLiberada(String camillaId, String fechaLiberacion, String origen) {
        log.info("📩 Procesando evento de liberación: camilla={}, origen={}, fecha={}",
                camillaId, origen, fechaLiberacion);

        Optional<Camilla> camillaOpt = camillaRepositoryPort.findById(camillaId);
        camillaOpt.ifPresentOrElse(camilla -> {
            camilla.setEstado("Disponible");
            camilla.setPaciente(null);
            camilla.setFechaFin(fechaLiberacion);
            camillaRepositoryPort.save(camilla);
            log.info("✅ Camilla {} liberada correctamente desde origen: {}", camillaId, origen);
        }, () -> log.warn("⚠️ Camilla no encontrada con ID: {}", camillaId));
    }
}
