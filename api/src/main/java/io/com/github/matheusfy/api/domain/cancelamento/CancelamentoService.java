package io.com.github.matheusfy.api.domain.cancelamento;

import io.com.github.matheusfy.api.domain.cancelamento.dto.CancelaConsultaDTO;
import io.com.github.matheusfy.api.domain.cancelamento.dto.ConsultasCanceladasDTO;
import io.com.github.matheusfy.api.domain.consulta.Consulta;
import io.com.github.matheusfy.api.repository.CancelamentoRepository;
import io.com.github.matheusfy.api.repository.ConsultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CancelamentoService {

	@Autowired
	CancelamentoRepository cancelamentoRepository;

	@Autowired
	ConsultaRepository consultaRepository;

	public CancelamentoService() {
	}

	@Transactional
	public void cancelaConsulta(CancelaConsultaDTO cancelamento) {

		Consulta consulta = consultaRepository.getReferenceById(cancelamento.consultaId());

		Cancelamento cancelamentoEntity = new Cancelamento(null, consulta, cancelamento.motivo());
		consulta.cancelaConsulta();
		cancelamentoRepository.save(cancelamentoEntity);
	}

	public Page<ConsultasCanceladasDTO> listarConsultasCanceladas(Pageable pageable) {
		return cancelamentoRepository.findAll(pageable).map(ConsultasCanceladasDTO::new);
	}
}
