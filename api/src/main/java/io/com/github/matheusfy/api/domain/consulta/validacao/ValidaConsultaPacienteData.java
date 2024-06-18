package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.domain.consulta.Consulta;
import io.com.github.matheusfy.api.exception.ValidationException;
import io.com.github.matheusfy.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidaConsultaPacienteData implements IValidadorAgendamentoConsulta {

    @Autowired
    ConsultaRepository repository;

    public void valida(CadastroConsultaDTO cadastroConsulta) {
        Optional<Consulta> consultaPaciente = repository.buscaConsultaData(cadastroConsulta.dataHora(),
                cadastroConsulta.pacienteId());
        if (consultaPaciente.isPresent())
            throw new ValidationException(
                    "Paciente já possui consulta na data %s. Agende em outra data ou cancele o agendamento primeiro."
                            .formatted(cadastroConsulta.dataHora().toLocalDate()));
    }
}
