package io.com.github.matheusfy.api.domain.paciente.dto;

import java.time.LocalDateTime;

import io.com.github.matheusfy.api.domain.endereco.EnderecoDTO;
import io.com.github.matheusfy.api.domain.paciente.Paciente;

public record pacienteUpdateDTO(

        Long id,
        String nome,
        String telefone,
        String email,
        EnderecoDTO enderecoDTO,
        LocalDateTime lastUpdate) {
    public pacienteUpdateDTO(Paciente p) {
        this(p.getId(), p.getNome(), p.getTelefone(), p.getEmail(), new EnderecoDTO(p.getEndereco()),
                p.getLastUpdate());
    }
}
