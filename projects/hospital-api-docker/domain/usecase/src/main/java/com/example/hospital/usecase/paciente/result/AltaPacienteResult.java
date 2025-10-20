package com.example.hospital.usecase.paciente.result;

import com.example.hospital.model.Camilla;
import com.example.hospital.model.Paciente;

/**
 * 🩺 Resultado del proceso de alta de paciente.
 * Contiene el paciente dado de alta y la camilla liberada.
 * Representa un contenedor inmutable con los datos finales del proceso.
 * Un record es ideal cuando tu clase:
 * ✅ Solo transporta datos (como un DTO o resultado).
 * ✅ No tiene lógica interna relevante (solo getters, constructor y equals/hashCode).
 * ✅ Es inmutable (no necesitas modificar sus campos después de crearlo).
 * ✅ Su propósito es “transportar información entre capas o métodos”.
 */
public record AltaPacienteResult(Paciente paciente, Camilla camilla) {}
