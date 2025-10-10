package com.example.hospitalapi.graphql.resolver;

import com.example.hospitalapi.model.Paciente;
import com.example.hospitalapi.service.PacienteService;
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

    private final PacienteService pacienteService;

    public PacienteQueryResolver(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Consulta todos los pacientes registrados.
     */
    @QueryMapping
    public List<Paciente> obtenerPacientes() {
        log.info("📥 Consulta GraphQL: obtenerPacientes()");
        List<Paciente> lista = pacienteService.obtenerTodos();
        log.info("📤 Total pacientes encontrados: {}", lista.size());
        return lista;
    }

    /**
     * Consulta un paciente por su ID.
     */
    @QueryMapping
    public Paciente pacientePorId(@Argument String id) {
        log.info("📥 Consulta GraphQL: pacientePorId(id={})", id);
        Optional<Paciente> paciente = pacienteService.obtenerPorId(id);
        if (paciente.isPresent()) {
            log.info("📤 Paciente encontrado: {}", paciente.get().getNombres());
            return paciente.get();
        } else {
            log.warn("⚠️ No se encontró paciente con ID {}", id);
            return null;
        }
    }
}
