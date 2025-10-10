package com.example.hospitalapi.graphql.mutation;



import com.example.hospitalapi.graphql.service.MockDataService;
import com.example.hospitalapi.model.Camilla;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;

/**
 * Resolver para las mutations MOCK del esquema GraphQL:
 * - liberarCamillaMock
 */
@Controller
public class CamillaMutationResolverMock {

    private final MockDataService mockDataService;

    public CamillaMutationResolverMock(MockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    /** Resuelve la mutation "liberarCamillaMock" */
    @MutationMapping(name = "liberarCamillaMock")
    public Camilla liberarCamillaMock(@Argument String idCamilla, @Argument String fechaFin) {
        return mockDataService.liberarCamilla(idCamilla, fechaFin);
    }
}
