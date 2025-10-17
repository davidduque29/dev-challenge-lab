package com.example.hospitalapi.repository;

import com.example.hospitalapi.document.CamillaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CamillaRepository extends MongoRepository<CamillaDocument, String> {
    List<CamillaDocument> findByEstado(String estado);

    //  Busca la camilla cuyo @DBRef paciente tiene este id
    Optional<CamillaDocument> findByPaciente_Id(String pacienteId);
}

