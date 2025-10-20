package com.example.hospital.adapters.mapper;

import com.example.hospital.adapters.document.HospitalDocument;
import com.example.hospital.model.Hospital;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HospitalMongoMapper {

    Hospital toDomain(HospitalDocument doc);

    HospitalDocument toDocument(Hospital domain);
}

