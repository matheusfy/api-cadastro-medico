package io.com.github.matheusfy.api.domain.paciente.dto;

import io.com.github.matheusfy.api.domain.endereco.EnderecoDTO;
import jakarta.validation.constraints.NotNull;


public record pacienteUpdateDTO(

    @NotNull
    Long id,
    String nome,
    String telefone,
    EnderecoDTO enderecoDTO
) {
}
