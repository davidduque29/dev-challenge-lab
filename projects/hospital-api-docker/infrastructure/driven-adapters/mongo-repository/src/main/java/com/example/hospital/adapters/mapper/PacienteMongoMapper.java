package com.example.hospital.adapters.mapper;


import com.example.hospital.adapters.document.PacienteDocument;

import com.example.hospital.model.Paciente;
import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PacienteMongoMapper {

    // Document -> Domain
    Paciente toDomain(PacienteDocument doc);

    // Domain -> Document
    PacienteDocument toDocument(Paciente paciente);
}
