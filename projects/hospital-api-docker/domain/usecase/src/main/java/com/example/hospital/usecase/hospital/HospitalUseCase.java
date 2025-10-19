package com.example.hospital.usecase.hospital;

import com.example.hospital.document.HospitalDocument;
import com.example.hospital.ports.out.HospitalRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 🧠 Caso de uso principal para la gestión de hospitales.
 */
@Slf4j
@RequiredArgsConstructor
public class HospitalUseCase {

    private final HospitalRepositoryPort hospitalRepository;

    /**
     * Retorna todos los hospitales.
     */
    public List<HospitalDocument> obtenerTodosLosHospitales() {
        log.info("🏥 Obteniendo todos los hospitales...");
        return hospitalRepository.findAll();
    }

    /**
     * Retorna hospitales por ciudad.
     */
    public List<HospitalDocument> obtenerHospitalesPorCiudad(String city) {
        log.info("🌆 Buscando hospitales en la ciudad: {}", city);
        return hospitalRepository.findByCity(city);
    }

    /**
     * Busca un hospital por su ID.
     */
    public Optional<HospitalDocument> obtenerHospitalPorId(String id) {
        log.info("🔍 Buscando hospital por ID: {}", id);
        return hospitalRepository.findById(id);
    }

    /**
     * Crea o actualiza un hospital.
     */
    public HospitalDocument crearHospital(HospitalDocument hospital) {
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
