package io.com.github.matheusfy.api.domain.consulta.dto;

import io.com.github.matheusfy.api.domain.consulta.Consulta;

import java.time.LocalDateTime;

public record ConsultaCadastradaDTO(
    Long id,
    Long medicoId,
    Long pacienteId,
    LocalDateTime data) {
    public ConsultaCadastradaDTO(Consulta c) {
        this(c.getId(), c.getMedico().getId(), c.getPaciente().getId(), c.getDataHora());
    }
}
