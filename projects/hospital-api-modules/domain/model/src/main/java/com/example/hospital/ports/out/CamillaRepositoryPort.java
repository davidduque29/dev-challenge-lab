package com.example.hospital.ports.out;

// 📂 dominio/puertos/out
import com.example.hospital.document.CamillaDocument;

import java.util.List;
import java.util.Optional;

public interface CamillaRepositoryPort {

    List<CamillaDocument> findAll();

    List<CamillaDocument> findByEstado(String estado);

    Optional<CamillaDocument> findById(String id);

    Optional<CamillaDocument> findByPacienteId(String pacienteId);

    CamillaDocument save(CamillaDocument camilla);

    void deleteById(String id);

    boolean existsById(String id);
}

