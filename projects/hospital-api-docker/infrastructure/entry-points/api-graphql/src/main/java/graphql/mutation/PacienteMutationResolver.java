package graphql.mutation;

import com.example.hospital.document.PacienteDocument;
import com.example.hospital.usecase.paciente.PacienteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 🎯 Resolver GraphQL para manejar las Mutations relacionadas con pacientes:
 * - Crear nuevo paciente
 * - Actualizar paciente existente
 * - Eliminar paciente
 */
@Controller
public class PacienteMutationResolver {

    private static final Logger log = LoggerFactory.getLogger(PacienteMutationResolver.class);
    private final PacienteUseCase pacienteService;

    public PacienteMutationResolver(PacienteUseCase pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * 🧍‍♂️ Crear un nuevo paciente en la base de datos (MongoDB).
     */
    @MutationMapping
    public PacienteDocument crearPaciente(
            @Argument String primerNombre,
            @Argument String segundoNombre,
            @Argument String primerApellido,
            @Argument String segundoApellido,
            @Argument String documentoIdentidad,
            @Argument String fechaNacimiento, // formato esperado: yyyy-MM-dd
            @Argument String tipoSangre,
            @Argument String genero,
            @Argument String alergias,
            @Argument String estado,
            @Argument String numeroHistoriaClinica,
            @Argument String eps
    ) {
        log.info("🧬 [GraphQL] Iniciando creación de paciente: {} {}", primerNombre, primerApellido);
        System.out.println("cmd🧬 [GraphQL] Iniciando creación de paciente: {} {}");
        PacienteDocument paciente = construirPaciente(
                primerNombre, segundoNombre, primerApellido, segundoApellido,
                documentoIdentidad, fechaNacimiento, tipoSangre, genero,
                alergias, estado, numeroHistoriaClinica, eps
        );

        PacienteDocument guardado = pacienteService.crearPaciente(paciente);
        log.info("✅ Paciente creado correctamente con ID: {}", guardado.getId());
        return guardado;
    }

    /**
     * 🧱 Construye un objeto PacienteDocument a partir de los argumentos GraphQL.
     */
    private PacienteDocument construirPaciente(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String documentoIdentidad, String fechaNacimiento, String tipoSangre, String genero,
            String alergias, String estado, String numeroHistoriaClinica, String eps
    ) {
        PacienteDocument paciente = new PacienteDocument();
        paciente.setPrimerNombre(primerNombre);
        paciente.setSegundoNombre(segundoNombre);
        paciente.setPrimerApellido(primerApellido);
        paciente.setSegundoApellido(segundoApellido);
        paciente.setDocumentoIdentidad(documentoIdentidad);
        paciente.setFechaNacimiento(parseFecha(fechaNacimiento));
        paciente.setTipoSangre(tipoSangre);
        paciente.setGenero(genero);
        paciente.setAlergias(alergias);
        paciente.setEstado(estado);
        paciente.setNumeroHistoriaClinica(numeroHistoriaClinica);
        paciente.setEps(eps);
        return paciente;
    }

    /**
     * 🗓️ Convierte una cadena a un objeto Date (formato yyyy-MM-dd).
     */
    private Date parseFecha(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isBlank()) {
            log.warn("⚠️ Fecha de nacimiento vacía o nula, se omitirá el valor.");
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
        } catch (ParseException e) {
            log.error("❌ Error al convertir la fecha '{}': {}", fechaNacimiento, e.getMessage());
            throw new IllegalArgumentException("Formato de fecha inválido. Usa yyyy-MM-dd");
        }
    }


    /**
     * ♻️ Actualizar un paciente existente.
     */
    @MutationMapping
    public PacienteDocument actualizarPaciente(
            @Argument String id,
            @Argument String primerNombre,
            @Argument String segundoNombre,
            @Argument String primerApellido,
            @Argument String segundoApellido,
            @Argument String documentoIdentidad,
            @Argument String fechaNacimiento,
            @Argument String tipoSangre,
            @Argument String genero,
            @Argument String alergias,
            @Argument String estado,
            @Argument String fechaAlta,
            @Argument String numeroHistoriaClinica,
            @Argument String eps
    ) {
        log.info("♻️ [GraphQL] Mutation → actualizarPaciente() para ID: {}", id);
        return pacienteService.actualizarPaciente(
                id, primerNombre, segundoNombre, primerApellido, segundoApellido,
                documentoIdentidad, fechaNacimiento, tipoSangre, genero,
                alergias, estado, fechaAlta, numeroHistoriaClinica, eps
        );
    }

    /**
     * 🗑️ Eliminar un paciente por su ID.
     */
    @MutationMapping
    public String eliminarPaciente(@Argument String id) {
        log.info("🗑️ [GraphQL] Mutation → eliminarPaciente() ID: {}", id);
        pacienteService.eliminarPaciente(id);
        return "✅ Paciente eliminado exitosamente con ID: " + id;
    }
}
