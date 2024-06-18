package io.com.github.matheusfy.api.domain.cancelamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CancelaConsultaDTO(

    @NotNull(message = "Não foi especificada qual consulta cancelar.")
    Long consultaId,

    @NotBlank(message = "Motivo não foi preenchido") String motivo) {

    @Override
    public String toString() {
        return "CancelaConsultaDTO: " + "consultaId=" + consultaId + ", motivo='" + motivo;
    }
}
