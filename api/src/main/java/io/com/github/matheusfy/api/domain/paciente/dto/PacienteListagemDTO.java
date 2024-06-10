package io.com.github.matheusfy.api.domain.paciente.dto;

import io.com.github.matheusfy.api.domain.paciente.Paciente;

public record PacienteListagemDTO(

    String nome,
    String email,
    String cpf
) {
    public PacienteListagemDTO(Paciente p){
        this(p.getNome(), p.getEmail(), p.getCpf());
    }
}
