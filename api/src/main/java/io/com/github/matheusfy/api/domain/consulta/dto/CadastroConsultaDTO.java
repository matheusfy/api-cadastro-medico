package io.com.github.matheusfy.api.domain.consulta.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.com.github.matheusfy.api.domain.enums.TipoEspecialidade;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CadastroConsultaDTO(

        Long medicoId,

        @NotNull(message = "Paciente não pode vir vazio")
        Long pacienteId,

        @NotNull(message = "Data e hora da consulta não pode ser vazia")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @FutureOrPresent
        LocalDateTime dataHora,

        TipoEspecialidade especialidade
) {
}
