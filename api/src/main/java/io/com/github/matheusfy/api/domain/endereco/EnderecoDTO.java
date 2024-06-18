package io.com.github.matheusfy.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(

        @NotBlank String logradouro,

        @NotBlank String bairro,

        @NotBlank String cidade,

        @NotBlank String uf,

        @NotBlank @Pattern(regexp = "\\d{8}") String cep,

        String numero,
        String complemento

) {
    public EnderecoDTO(Endereco endereco) {
        this(endereco.getLogradouro(), endereco.getBairro(), endereco.getCidade(), endereco.getUf(), endereco.getCep(),
                endereco.getNumero(), endereco.getComplemento());
    }
}
