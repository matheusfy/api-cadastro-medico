package io.com.github.matheusfy.api.controller.usuario;

import io.com.github.matheusfy.api.controller.UserAuthenticationDTO;
import io.com.github.matheusfy.api.domain.usuario.Usuario;
import io.com.github.matheusfy.api.infra.security.DadosTokenDTO;
import io.com.github.matheusfy.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserAuthenticationController {

	@Autowired
	AuthenticationManager manager;

	@Autowired
	TokenService tokenService;

	@PostMapping
	public ResponseEntity<DadosTokenDTO> LoginUser(@RequestBody @Valid UserAuthenticationDTO userLogin) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLogin.login(),
				userLogin.senha());
		Authentication authentication = manager.authenticate(token);
		String tokenJWT = tokenService.generateToken((Usuario) authentication.getPrincipal());

		return ResponseEntity.ok(new DadosTokenDTO(tokenJWT));
	}
}
