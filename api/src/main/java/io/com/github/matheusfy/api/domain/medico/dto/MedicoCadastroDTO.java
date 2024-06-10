package io.com.github.matheusfy.api.domain.medico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.com.github.matheusfy.api.domain.endereco.EnderecoDTO;
import io.com.github.matheusfy.api.domain.enums.TipoEspecialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;


public record MedicoCadastroDTO(

    @NotBlank
     String nome,

    @NotBlank
        @Email
     String email,

    @NotBlank
        @Pattern(regexp = "\\d{4,6}")
     String crm,

    @NotBlank
        @Pattern(regexp = "\\d{10}")
    String telefone,

    @NotNull
    @JsonProperty("especialidade")
     TipoEspecialidade especialidade,

     @NonNull
         @Valid
    EnderecoDTO endereco
){

}
