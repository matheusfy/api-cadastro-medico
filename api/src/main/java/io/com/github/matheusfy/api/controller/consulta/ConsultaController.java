package io.com.github.matheusfy.api.controller.consulta;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.domain.consulta.ConsultaService;
import io.com.github.matheusfy.api.domain.consulta.dto.ConsultaCadastradaDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    ConsultaService consultaService;

    @GetMapping("/{pacienteId}")
    public ResponseEntity<Page<ConsultaCadastradaDTO>> buscaConsultasPaciente(@PathVariable Long pacienteId,
            Pageable pageable) {

        return ResponseEntity.ok(consultaService.buscaConsultasPaciente(pacienteId, pageable));
    }

    @PostMapping
    public ResponseEntity<ConsultaCadastradaDTO> cadastraConsulta(
            @Valid @RequestBody CadastroConsultaDTO cadastroConsulta) {
        return ResponseEntity.ok(consultaService.cadastrarConsulta(cadastroConsulta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removerConsulta(@PathVariable Long id) {
        consultaService.removeConsulta(id);
        return ResponseEntity.noContent().build();
    }

}
