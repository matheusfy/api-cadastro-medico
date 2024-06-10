package io.com.github.matheusfy.api.domain.paciente.dto;

import io.com.github.matheusfy.api.domain.endereco.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;

public record CadastroPacienteDTO(

    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank @Pattern(regexp = "\\d{10}") String telefone,
    @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
    @NonNull EnderecoDTO endereco

) {


}
