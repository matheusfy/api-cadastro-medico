package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ValidaMedicoExiste implements IValidadorAgendamentoConsulta {

    @Autowired
    MedicoRepository repository;

    @Override
    public void valida(CadastroConsultaDTO cadastroConsulta) {

        if (isNull(cadastroConsulta.medicoId())) {
            return;
        }

        boolean existe = repository.existsById(cadastroConsulta.medicoId());
        if (!existe)
            throw new ValidationException("Médico solicitado não existe no sistema!");
    }
}
