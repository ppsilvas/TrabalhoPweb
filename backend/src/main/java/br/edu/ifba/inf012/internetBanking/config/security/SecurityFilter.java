package br.edu.ifba.inf012.internetBanking.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifba.inf012.internetBanking.repositories.UsuarioRepository;
import br.edu.ifba.inf012.internetBanking.services.JWTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	private JWTokenService jwtService;
	
	private UsuarioRepository usuarioRepository;

	public SecurityFilter(JWTokenService jwtService, UsuarioRepository usuarioRepository) {
		super();
		this.jwtService = jwtService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		var token = this.jwtService.recuperarToken(request);
		if(token != null) {
			var login = this.jwtService.getSubject(token);
			var usuario = this.usuarioRepository.findByEmail(login);
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication	);
		}
		chain.doFilter(request, response);
	}

}
