package io.com.github.matheusfy.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record CadastroConsultaDTO(

    String medicoNome,

    @NotBlank(message = "Nome do paciente não pode ser vazio")
    String pacienteNome,

    @NotNull(message = "Data e hora da consulta não pode ser vazia")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
        @FutureOrPresent
    LocalDateTime dataHora
) {
}
