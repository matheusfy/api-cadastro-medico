package io.com.github.matheusfy.api.domain.medico.dto;

import io.com.github.matheusfy.api.domain.endereco.Endereco;
import io.com.github.matheusfy.api.domain.enums.TipoEspecialidade;
import io.com.github.matheusfy.api.domain.medico.Medico;

import java.time.LocalDateTime;

public record MedicoDetalheDTO(
    Long id,
    String nome,
    String email,
    String crm,
    String telefone,

    TipoEspecialidade especialidade,
    Endereco endereco,
    boolean ativo,
    LocalDateTime lastUpdate
) {
    public MedicoDetalheDTO(Medico m) {
        this(m.getId(), m.getNome(), m.getEmail(),m.getCrm(), m.getTelefone(), m.getEspecialidade(), m.getEndereco(), m.isAtivo(), m.getLastUpdate());
    }
}
