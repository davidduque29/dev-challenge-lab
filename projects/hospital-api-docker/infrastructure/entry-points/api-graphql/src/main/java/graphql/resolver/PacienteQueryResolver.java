package graphql.resolver;


import com.example.hospital.document.PacienteDocument;
import com.example.hospital.usecase.paciente.PacienteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * Resolver GraphQL para consultas de pacientes (lectura).
 */
@Controller
public class PacienteQueryResolver {

    private static final Logger log = LoggerFactory.getLogger(PacienteQueryResolver.class);

    private final PacienteUseCase pacienteService;

    public PacienteQueryResolver(PacienteUseCase pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Consulta todos los pacientes registrados.
     */
    @QueryMapping
    public List<PacienteDocument> obtenerPacientes() {
        log.info("📥 Consulta GraphQL: obtenerPacientes()");
        List<PacienteDocument> lista = pacienteService.obtenerTodosLosPacientes();
        log.info("📤 Total pacientes encontrados: {}", lista.size());
        System.out.println("PacienteQueryResolver - Obteniendo todos los pacientes..."+lista.size());
        return lista;
    }

    /**
     * Consulta un paciente por su ID.
     */
    @QueryMapping
    public PacienteDocument pacientePorId(@Argument String id) {
        log.info("📥 Consulta GraphQL: pacientePorId(id={})", id);
        Optional<PacienteDocument> paciente = pacienteService.obtenerPacientePorId(id);
        if (paciente.isPresent()) {
            log.info("📤 Paciente encontrado: {}", paciente.get().getPrimerNombre()
                    +" "+paciente.get().getPrimerApellido()+" (ID: "+paciente.get().getId()+")");
            return paciente.get();
        } else {
            log.warn("⚠️ No se encontró paciente con ID {}", id);
            return null;
        }
    }
}
