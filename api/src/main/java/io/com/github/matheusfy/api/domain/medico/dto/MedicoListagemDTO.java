package io.com.github.matheusfy.api.domain.medico.dto;

import io.com.github.matheusfy.api.domain.medico.Medico;

public record MedicoListagemDTO(
    Long id,
    String nome,
    String email,
    String crm,
    String telefone

){
    public MedicoListagemDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone());
    }

}
