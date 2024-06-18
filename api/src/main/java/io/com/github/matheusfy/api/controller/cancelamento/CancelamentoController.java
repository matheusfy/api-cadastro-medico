package io.com.github.matheusfy.api.controller.cancelamento;

import io.com.github.matheusfy.api.domain.cancelamento.CancelamentoService;
import io.com.github.matheusfy.api.domain.cancelamento.dto.CancelaConsultaDTO;
import io.com.github.matheusfy.api.domain.cancelamento.dto.ConsultasCanceladasDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cancelamento")
public class CancelamentoController {

	@Autowired
	CancelamentoService cancelamentoService;

	@GetMapping
	public ResponseEntity<Page<ConsultasCanceladasDTO>> listarConsultasCanceladas(Pageable pageable) {
		return ResponseEntity.ok(cancelamentoService.listarConsultasCanceladas(pageable));
	}

	@PostMapping
	public ResponseEntity<Object> cancelaConsulta(@RequestBody @Valid CancelaConsultaDTO cancelaConsultaDTO) {

		cancelamentoService.cancelaConsulta(cancelaConsultaDTO);
		return ResponseEntity.ok().build();
	}

}
