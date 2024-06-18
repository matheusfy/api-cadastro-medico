package io.com.github.matheusfy.api.domain.cancelamento.validacao;

import org.springframework.beans.factory.annotation.Autowired;

import io.com.github.matheusfy.api.domain.cancelamento.dto.CancelaConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.ConsultaRepository;

public class ConsultaExiste implements CancelamentoValidation {

	@Autowired
	ConsultaRepository consultaRepository;

	@Override
	public void validar(CancelaConsultaDTO cancelaConsultaDTO) {
		boolean existe = consultaRepository.existsById(cancelaConsultaDTO.consultaId());
		if (!existe) {
			throw new ValidationException("Consulta não encontrada para cancelamento");
		}
	}
}
