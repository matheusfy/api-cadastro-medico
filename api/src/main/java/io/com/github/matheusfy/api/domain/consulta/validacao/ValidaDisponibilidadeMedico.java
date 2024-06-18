package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaDisponibilidadeMedico implements IValidadorAgendamentoConsulta {

    @Autowired
    MedicoRepository repository;

    @Override
    public void valida(CadastroConsultaDTO cadastroConsulta) {
        if (cadastroConsulta.medicoId() == null)
            return;
        if (repository.medicoPossuiConsulta(cadastroConsulta.medicoId(), cadastroConsulta.dataHora()) > 0) {
            throw new ValidationException("Médico indisponível na data %s ".formatted(cadastroConsulta.dataHora()));
        }
    }
}
