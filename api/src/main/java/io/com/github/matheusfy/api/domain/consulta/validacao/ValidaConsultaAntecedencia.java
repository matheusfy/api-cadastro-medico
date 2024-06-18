package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class ValidaConsultaAntecedencia implements IValidadorAgendamentoConsulta {

    public void valida(CadastroConsultaDTO cadastroConsultaDTO) {
        LocalDateTime dataHora = cadastroConsultaDTO.dataHora();
        if (LocalDateTime.now().until(dataHora, ChronoUnit.MINUTES) <= 30) {
            throw new ValidationException(
                    "O horário da consulta deve ser agendada com 30min de antecedência!: " + dataHora);
        }
    }
}
