package io.com.github.matheusfy.api.domain.consulta.validacao;

import io.com.github.matheusfy.api.domain.consulta.dto.CadastroConsultaDTO;

public interface IValidadorAgendamentoConsulta {

    public void valida(CadastroConsultaDTO cadastroConsulta);
}
