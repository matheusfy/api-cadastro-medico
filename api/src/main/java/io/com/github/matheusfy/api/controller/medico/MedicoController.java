package io.com.github.matheusfy.api.controller.medico;

import io.com.github.matheusfy.api.domain.medico.Medico;
import io.com.github.matheusfy.api.domain.medico.MedicoService;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoCadastroDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoDetalheDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoListagemDTO;
import io.com.github.matheusfy.api.domain.medico.dto.MedicoUpdateDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medico")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    MedicoService service;

    @GetMapping("/listagem")
    public ResponseEntity<Page<MedicoListagemDTO>> listagemMedicos(Pageable pageable){
        return ResponseEntity.ok(service.buscaListaMedicos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoListagemDTO> listarMedico(@PathVariable Long id){
        return ResponseEntity.ok(service.buscaMedicoById(id));
    }

    @PostMapping
    public ResponseEntity<MedicoDetalheDTO> cadastrar(@RequestBody  @Valid MedicoCadastroDTO dadosMedico, UriComponentsBuilder uriBuilder){
        Medico medico = service.cadastrarMedico(dadosMedico);

        URI uri = uriBuilder.path("/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicoDetalheDTO(medico));
    }

    @PutMapping
    public ResponseEntity<MedicoDetalheDTO> atualizar(@RequestBody @Valid MedicoUpdateDTO dadosMedicoAtualizado){
        return ResponseEntity.ok(service.updateMedico(dadosMedicoAtualizado));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removerMedico(@PathVariable("id") Long id){
        service.desativaMedico(id);
        return ResponseEntity.noContent().build();
    }
}
