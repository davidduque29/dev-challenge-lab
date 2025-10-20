package com.example.hospital.usecase.hospital;

import com.example.hospital.model.Hospital;
import com.example.hospital.ports.out.HospitalRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 🧠 Caso de uso principal para la gestión de hospitales.
 * Mantiene la lógica de negocio desacoplada de la infraestructura.
 */
@Slf4j
@RequiredArgsConstructor
public class HospitalUseCase {

    private final HospitalRepositoryPort hospitalRepository;

    /**
     * Retorna todos los hospitales.
     */
    public List<Hospital> obtenerTodosLosHospitales() {
        log.info("🏥 Obteniendo todos los hospitales...");
        return hospitalRepository.findAll();
    }

    /**
     * Retorna hospitales por ciudad.
     */
    public List<Hospital> obtenerHospitalesPorCiudad(String city) {
        log.info("🌆 Buscando hospitales en la ciudad: {}", city);
        return hospitalRepository.findByCity(city);
    }

    /**
     * Busca un hospital por su ID.
     */
    public Optional<Hospital> obtenerHospitalPorId(String id) {
        log.info("🔍 Buscando hospital por ID: {}", id);
        return hospitalRepository.findById(id);
    }

    /**
     * Crea o actualiza un hospital.
     */
    public Hospital crearHospital(Hospital hospital) {
        log.info("🛠️ Guardando hospital: {}", hospital.getName());
        return hospitalRepository.save(hospital);
    }

    /**
     * Elimina un hospital.
     */
    public void eliminarHospital(String id) {
        log.info("🗑️ Eliminando hospital con ID: {}", id);
        if (!hospitalRepository.existsById(id)) {
            log.warn("⚠️ Hospital no encontrado con ID: {}", id);
            throw new RuntimeException("Hospital no encontrado con ID: " + id);
        }
        hospitalRepository.deleteById(id);
        log.info("✅ Hospital eliminado exitosamente");
    }
}
