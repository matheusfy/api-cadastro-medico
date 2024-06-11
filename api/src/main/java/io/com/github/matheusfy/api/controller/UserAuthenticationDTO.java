package io.com.github.matheusfy.api.controller;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationDTO(

		@NotBlank String login,
		@NotBlank String senha) {

}
