package io.com.github.matheusfy.api.domain.endereco;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(

    @NotBlank
    String logradouro,

    @NotBlank
    String bairro,

    @NotBlank
    String cidade,

    @NotBlank
    String uf,

    @NotBlank
        @Pattern(regexp = "\\d{8}")
    String cep,

    String numero,
    String complemento

    ) {
}

//new EnderecoDTO(medico.getEndereco().getLogradouro(),
//    medico.getEndereco().getBairro(),
//    medico.getEndereco().getCidade(),
//    medico.getEndereco().getUf(),
//    medico.getEndereco().getCep(),
//    medico.getEndereco().getNumero(),
//    medico.getEndereco().getComplemento())