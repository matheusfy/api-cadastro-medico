package io.com.github.matheusfy.api.domain.cancelamento.validacao;

import io.com.github.matheusfy.api.domain.cancelamento.dto.CancelaConsultaDTO;

public interface CancelamentoValidation {
	public void validar(CancelaConsultaDTO cancelaConsultaDTO);
}
