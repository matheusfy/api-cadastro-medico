package io.com.github.matheusfy.api.domain.cancelamento.dto;

import io.com.github.matheusfy.api.domain.cancelamento.Cancelamento;

public record ConsultasCanceladasDTO(
		Long id,
		Long consultaId,
		String motivo) {
	public ConsultasCanceladasDTO(Cancelamento cancelamento) {
		this(cancelamento.getId(), cancelamento.getConsulta().getId(), cancelamento.getMotivo());
	}
}
