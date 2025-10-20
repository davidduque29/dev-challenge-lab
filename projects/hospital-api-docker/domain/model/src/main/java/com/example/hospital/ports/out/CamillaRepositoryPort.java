package com.example.hospital.ports.out;

// 📂 dominio/puertos/out


import com.example.hospital.model.Camilla;

import java.util.List;
import java.util.Optional;

public interface CamillaRepositoryPort {

    List<Camilla> findAll();

    List<Camilla> findByEstado(String estado);

    Optional<Camilla> findById(String id);

    Optional<Camilla> findByPacienteId(String pacienteId);

    Camilla save(Camilla camilla);

    void deleteById(String id);

    boolean existsById(String id);
}


