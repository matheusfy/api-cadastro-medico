package io.com.github.matheusfy.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.com.github.matheusfy.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

	private final String secret;

	private final String ISSUER = "api-voll-med";

	public TokenService(@Value("${api.security.token.secret}") String secret) {
		this.secret = secret;
	}

	public String generateToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer(ISSUER)
					.withSubject(usuario.getLogin())
					.withExpiresAt(dataExpiracao())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Falha na geracao do token!" + exception.getMessage());
		}
	}

	public String getSubject(String tokenJWT){
		Algorithm algorithm = Algorithm.HMAC256(secret);
		try{
			return JWT.require(algorithm)
				.withIssuer(ISSUER)
				.build()
				.verify(tokenJWT)
				.getSubject();
		} catch (JWTVerificationException exception){
			throw new RuntimeException("Token JWT inválido ou expirado!");
		}

	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
