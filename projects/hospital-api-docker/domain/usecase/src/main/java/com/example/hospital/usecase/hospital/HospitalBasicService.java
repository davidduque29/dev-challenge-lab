package com.example.hospital.usecase.hospital;

 import com.example.hospital.model.Hospital;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class HospitalBasicService {

    private final List<Hospital> hospitalList = Arrays.asList(
            new Hospital(1001, "Apollo Hospital", "Chennai", 3.8),
            new Hospital(1002, "Global Hospital", "Chennai", 3.5),
            new Hospital(1003, "Vcare Hospital", "Bangalore", 3.0)
    );

    private final List<Hospital> hospitalbigList = Arrays.asList(
            new Hospital(1001, "Apollo Hospital", "Chennai", 3.8),
            new Hospital(1002, "Global Hospital", "Chennai", 3.5),
            new Hospital(1003, "Vcare Hospital", "Bangalore", 3.0),
            new Hospital(1004, "Fortis Hospital", "Mumbai", 4.1),
            new Hospital(1005, "Max Super Speciality Hospital", "Delhi", 4.3),
            new Hospital(1006, "Kauvery Hospital", "Trichy", 3.7),
            new Hospital(1007, "Manipal Hospital", "Hyderabad", 4.0),
            new Hospital(1008, "Columbia Asia Hospital", "Pune", 3.9),
            new Hospital(1009, "Aster Medcity", "Kochi", 4.4),
            new Hospital(1010, "Amrita Institute of Medical Sciences", "Kochi", 4.5),
            new Hospital(1011, "Hinduja Hospital", "Mumbai", 4.2),
            new Hospital(1012, "Narayana Health", "Bangalore", 4.3),
            new Hospital(1013, "Medanta The Medicity", "Gurgaon", 4.6),
            new Hospital(1014, "AIIMS Hospital", "Delhi", 4.7),
            new Hospital(1015, "Care Hospital", "Hyderabad", 3.9)
    );

    public List<Hospital> getHospitalsByPage(int page, int size) {
        // Calcula el total de hospitales disponibles
        int total = hospitalList.size();
        // Validaciones para evitar valores negativos o cero
        if (page < 1) page = 1;  // Si se envía un número de página menor que 1, se ajusta a 1
        if (size < 1) size = 5;  // Si el tamaño por página es menor que 1, se usa un valor por defecto (5)

        // Calcula el índice inicial del corte (ejemplo: page=2, size=5 → start=5)
        int start = (page - 1) * size;

        // Si el índice inicial es mayor o igual al total, significa que no hay más datos
        if (start >= total) {
            // Retorna una lista vacía (sin lanzar excepción)
            return Collections.emptyList();
        }

        // Calcula el índice final del corte.
        // Math.min se usa para evitar que el final supere el tamaño total de la lista.
        int end = Math.min(start + size, total);

        // Devuelve la sublista desde el índice start hasta end (sin incluir end)
        // Esto simula la "paginación" típica de una consulta a base de datos.
        return hospitalList.subList(start, end);
    }


    public List<Hospital> getAllHospitals() {
        return hospitalList;
    }

    public Hospital getHospital(int id) {
        return hospitalList.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
