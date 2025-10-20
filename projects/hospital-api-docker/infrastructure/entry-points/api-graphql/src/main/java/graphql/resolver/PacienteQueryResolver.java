package graphql.resolver;


import com.example.hospital.model.Paciente;
import com.example.hospital.usecase.paciente.PacienteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * 🎯 Resolver GraphQL para consultas de pacientes (lectura).
 * Desacoplado de la capa de persistencia.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PacienteQueryResolver {

    private final PacienteUseCase pacienteUseCase;

    /**
     * Consulta todos los pacientes registrados.
     */
    @QueryMapping
    public List<Paciente> obtenerPacientes() {
        log.info("📥 Query GraphQL: obtenerPacientes()");
        List<Paciente> lista = pacienteUseCase.obtenerTodosLosPacientes();
        log.info("📤 Total pacientes encontrados: {}", lista.size());
        return lista;
    }

    /**
     * Consulta un paciente por su ID.
     */
    @QueryMapping
    public Paciente pacientePorId(@Argument String id) {
        log.info("📥 Query GraphQL: pacientePorId(id={})", id);
        Optional<Paciente> paciente = pacienteUseCase.obtenerPacientePorId(id);

        if (paciente.isPresent()) {
            log.info("✅ Paciente encontrado: {} {} (ID: {})",
                    paciente.get().getPrimerNombre(),
                    paciente.get().getPrimerApellido(),
                    paciente.get().getId());
            return paciente.get();
        } else {
            log.warn("⚠️ No se encontró paciente con ID {}", id);
            return null;
        }
    }
}
