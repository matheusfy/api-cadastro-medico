package io.com.github.matheusfy.api.infra.security.filter;

import io.com.github.matheusfy.api.domain.usuario.Usuario;
import io.com.github.matheusfy.api.infra.security.TokenService;
import io.com.github.matheusfy.api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final String AUTHORIZATION = "Authorization";

	@Autowired
	TokenService tokenService;

	@Autowired
	UsuarioRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String tokenJWT = recuperarToken(request);
		if (tokenJWT != null){
			String subject = tokenService.getSubject(tokenJWT);
			UserDetails user= userRepository.findByLogin(subject);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

			// Realiza o processo de autenticação por baixo dos panos.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("Logado na requisicao");
		}
		filterChain.doFilter(request,response);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (token != null){
			return token.replace("Bearer ", "").trim();
		}
		return null;
	}

}
