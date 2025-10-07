package com.example.hospitalapi;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class HospitalService {

    private final List<Hospital> hospitalList = Arrays.asList(
            new Hospital(1001, "Apollo Hospital", "Chennai", 3.8),
            new Hospital(1002, "Global Hospital", "Chennai", 3.5),
            new Hospital(1003, "Vcare Hospital", "Bangalore", 3.0)
    );

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
