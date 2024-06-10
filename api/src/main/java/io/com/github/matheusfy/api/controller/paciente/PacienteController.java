package io.com.github.matheusfy.api.controller.paciente;

import io.com.github.matheusfy.api.domain.paciente.PacienteService;
import io.com.github.matheusfy.api.domain.paciente.dto.CadastroPacienteDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.PacienteListagemDTO;
import io.com.github.matheusfy.api.domain.paciente.dto.pacienteUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/paciente")
public class PacienteController {

    @Autowired
    PacienteService service;

    @GetMapping
    public Page<PacienteListagemDTO> listarPaciente(Pageable pageable){
        return service.listarPacientes(pageable);
    }

    @PostMapping
    public String consulta(@RequestBody @Valid CadastroPacienteDTO cadastroPaciente){
        service.cadastrarPaciente(cadastroPaciente);
        return "cadastrado";
    }

    @PutMapping
    public String atualiza(@RequestBody @Valid pacienteUpdateDTO paciente){
        service.atualizarPaciente(paciente);
        return "deu bom";
    }

    @DeleteMapping("/{id}")
    public String deleta(@PathVariable("id") Long id){
        service.desativarPaciente(id);
        return "deu bom";
    }
}
