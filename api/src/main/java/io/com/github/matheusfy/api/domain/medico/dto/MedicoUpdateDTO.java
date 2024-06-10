package io.com.github.matheusfy.api.domain.medico.dto;

import io.com.github.matheusfy.api.domain.endereco.EnderecoDTO;
import jakarta.validation.constraints.NotNull;

public record MedicoUpdateDTO(

    @NotNull
    Long id,

    String nome,

    String telefone,

    EnderecoDTO endereco

) {
}
