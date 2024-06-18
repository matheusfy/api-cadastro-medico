package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ValidaMedicoAtivo implements IValidadorAgendamentoConsulta {

    @Autowired
    MedicoRepository repository;

    @Override
    public void valida(CadastroConsultaDTO cadastroConsulta) {

        if (isNull(cadastroConsulta.medicoId())) {
            return;
        }

        boolean ativo = repository.existsAtivoById(cadastroConsulta.medicoId());
        if (!ativo)
            throw new ValidationException("Médico solicitado está inativo!");
    }
}
