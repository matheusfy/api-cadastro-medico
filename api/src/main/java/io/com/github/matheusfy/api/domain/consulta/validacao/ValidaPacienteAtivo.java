package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements IValidadorAgendamentoConsulta {

    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public void valida(CadastroConsultaDTO cadastroConsulta) {
        boolean ativo = pacienteRepository.existsByIdAndAtivoTrue(cadastroConsulta.pacienteId());
        if (!ativo) {
            throw new ValidationException("Paciente não encontrado ou inativo");
        }
    }
}
