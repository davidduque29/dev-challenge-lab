package com.example.hospital.usecase.camilla;

import com.example.hospital.document.CamillaDocument;
import com.example.hospital.ports.out.CamillaRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class EventCamillaUseCase {

    private final CamillaRepositoryPort camillaRepositoryPort;

    public void procesarCamillaLiberada(String camillaId, String fechaLiberacion, String origen) {
        log.info("📩 Procesando evento de liberación: camilla={}, origen={}, fecha={}",
                camillaId, origen, fechaLiberacion);

        Optional<CamillaDocument> camillaOpt = camillaRepositoryPort.findById(camillaId);
        camillaOpt.ifPresentOrElse(camilla -> {
            camilla.setEstado("Disponible");
            camilla.setPaciente(null);
            camilla.setFechaFin(fechaLiberacion);
            camillaRepositoryPort.save(camilla);
            log.info("✅ Camilla {} liberada correctamente", camillaId);
        }, () -> log.warn("⚠️ Camilla no encontrada con ID: {}", camillaId));
    }
}

