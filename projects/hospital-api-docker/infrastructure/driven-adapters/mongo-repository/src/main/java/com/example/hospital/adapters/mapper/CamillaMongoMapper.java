package com.example.hospital.adapters.mapper;

import com.example.hospital.adapters.document.CamillaDocument;
import com.example.hospital.model.Camilla;
 import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper que convierte entre los modelos de dominio (Camilla, Paciente)
 * y los documentos de persistencia (CamillaDocument, PacienteDocument).
 */

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { PacienteMongoMapper.class } // 👈 clave
)
public interface CamillaMongoMapper {

    // Mapea el campo anidado paciente <-> paciente usando PacienteMongoMapper
    @Mapping(target = "paciente", source = "paciente")
    Camilla toDomain(CamillaDocument doc);

    @Mapping(target = "paciente", source = "paciente")
    CamillaDocument toDocument(Camilla domain);
}