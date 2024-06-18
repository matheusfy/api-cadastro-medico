package io.com.github.matheusfy.api.controller.paciente;

import io.com.github.matheusfy.api.domain.paciente.PacienteService;
import io.com.github.matheusfy.api.domain.paciente.dto.CadastroPacienteDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.PacienteListagemDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.pacienteUpdateDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/paciente")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    PacienteService service;

    @GetMapping
    public Page<PacienteListagemDTO> listarPaciente(Pageable pageable) {
        return service.listarPacientes(pageable);
    }

    @PostMapping
    public ResponseEntity<PacienteListagemDTO> consulta(@RequestBody @Valid CadastroPacienteDTO cadastroPaciente) {
        return ResponseEntity.ok(service.cadastrarPaciente(cadastroPaciente));
    }

    @PutMapping
    public ResponseEntity<pacienteUpdateDTO> atualiza(@RequestBody @Valid pacienteUpdateDTO paciente) {
        pacienteUpdateDTO pacienteAtualizacaoDTO = service.atualizarPaciente(paciente);
        return ResponseEntity.ok(pacienteAtualizacaoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleta(@PathVariable("id") Long id) {
        service.desativarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
