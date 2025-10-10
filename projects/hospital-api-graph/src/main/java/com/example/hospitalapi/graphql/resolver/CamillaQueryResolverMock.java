package com.example.hospitalapi.graphql.resolver;

import com.example.hospitalapi.graphql.service.MockDataService;
import com.example.hospitalapi.model.Camilla;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;


/**
 * Resolver para el esquema MOCK.
 * Expone las queries:
 * - camillasDisponiblesMock
 * - camillaPorIdMock
 */
@Controller
public class CamillaQueryResolverMock {

    private final MockDataService mockDataService;

    public CamillaQueryResolverMock(MockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    /** Resuelve la query "camillasDisponiblesMock" */
    @QueryMapping(name = "camillasDisponiblesMock")
    public List<Camilla> camillasDisponiblesMock() {
        return mockDataService.obtenerCamillasDisponibles();
    }

    /** Resuelve la query "camillaPorIdMock" */
    @QueryMapping(name = "camillaPorIdMock")
    public Camilla camillaPorIdMock(@Argument String idCamilla) {
        return mockDataService.obtenerCamillaPorId(idCamilla).orElse(null);
    }
}
