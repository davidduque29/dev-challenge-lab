package com.example.hospitalapi.service;
import com.example.hospitalapi.exception.MongoConnectionException;
import com.example.hospitalapi.document.Hospital;
import com.example.hospitalapi.repository.HospitalRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository repository;

    public HospitalService(HospitalRepository repository) {
        this.repository = repository;
    }

    public List<Hospital> getAll() {
        try {
            return repository.findAll();
        } catch (UncategorizedMongoDbException e) {
            throw new MongoConnectionException("Error al conectar con MongoDB Atlas. Verifica las credenciales o la red.", e);
        } catch (DataAccessException e) {
            throw new MongoConnectionException("Error de acceso a datos en MongoDB.", e);
        }
    }

    public Hospital save(Hospital hospital) {
        return repository.save(hospital);
    }

    public List<Hospital> findByCity(String city) {
        return repository.findByCity(city);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
