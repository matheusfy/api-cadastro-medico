package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;
import io.com.github.matheusfy.api.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioFuncionamento implements IValidadorAgendamentoConsulta {

    public void valida(CadastroConsultaDTO cadastroConsultaDTO) {
        LocalDateTime dataHora = cadastroConsultaDTO.dataHora();
        if ((dataHora.getDayOfWeek() == DayOfWeek.SUNDAY) && !validateHour(dataHora.getHour())) {
            throw new ValidationException("Não é possível marcar consulta neste dia/horário: " + dataHora);
        }
    }

    private boolean validateHour(int hour) {
        return (hour >= 7) && (hour <= 18);
    }
}
