package io.com.github.matheusfy.api.controller.consulta;

import io.com.github.matheusfy.api.domain.consulta.CadastroConsultaDTO;
import io.com.github.matheusfy.api.domain.consulta.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    ConsultaService service;

    @PostMapping
    public void cadastraConsulta(@Valid @RequestBody CadastroConsultaDTO cadastroConsulta){
        service.cadastrarConsulta(cadastroConsulta);
    }

}
