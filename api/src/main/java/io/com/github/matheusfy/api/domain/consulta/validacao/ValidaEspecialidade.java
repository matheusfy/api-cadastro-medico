package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidaEspecialidade implements IValidadorAgendamentoConsulta {

    @Override
    public void valida(CadastroConsultaDTO cadastroConsulta) {
        if (cadastroConsulta.medicoId() != null)
            return;
        if (cadastroConsulta.especialidade() == null)
            throw new ValidationException("Especialidade não pode ser vazia quando médico não é especidicado!");
    }
}
