package io.com.github.matheusfy.api.domain.cancelamento.validacao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.com.github.matheusfy.api.domain.cancelamento.dto.CancelaConsultaDTO;
import io.com.github.matheusfy.api.domain.consulta.Consulta;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.ConsultaRepository;

@Component
public class CancelamentoComAntecedencia implements CancelamentoValidation {

	@Autowired
	ConsultaRepository consultaRepository;

	@Override
	public void validar(CancelaConsultaDTO cancelaConsultaDTO) {
		// Valida se a consulta foi cancelada com antecedência
		Consulta consulta = consultaRepository.findReferenceById(cancelaConsultaDTO.consultaId());

		LocalDateTime dataUmDiaNoFuturo = LocalDateTime.now().plusDays(1).atZone(ZoneId.of("America/Sao_Paulo"))
				.toLocalDateTime();

		if (dataUmDiaNoFuturo.isAfter(consulta.getDataHora())) {
			throw new ValidationException("Não é possível cancelar a consulta com menos de 24 horas de antecedência");
		}
	}
}
