package io.com.github.matheusfy.api.domain.usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import io.com.github.matheusfy.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	UsuarioRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserDetails usuario = userRepository.findByLogin(login);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}
		return usuario;
	}
}
