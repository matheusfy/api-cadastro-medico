package io.com.github.matheusfy.api.domain.paciente.dto;

import io.com.github.matheusfy.api.domain.paciente.Paciente;

public record PacienteListagemDTO(

        Long id,
        String nome,
        String email) {
    public PacienteListagemDTO(Paciente p) {
        this(p.getId(), p.getNome(), p.getEmail());
    }
}
