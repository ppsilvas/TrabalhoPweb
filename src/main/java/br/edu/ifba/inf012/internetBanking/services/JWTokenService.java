package br.edu.ifba.inf012.internetBanking.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.edu.ifba.inf012.internetBanking.models.Usuario;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	@Value("${api.security.token.issuer}")
	private String issuer;
	
	public String gerarToken(Usuario usuario) {
		try {
			var algoritmo = Algorithm.HMAC256(secret);
			return JWT.create()
				.withIssuer(issuer)
				.withSubject(usuario.getEmail())
				.withClaim("userId", usuario.getId())
				.withClaim("numConta", usuario.getConta().getNumero())
				.withClaim("role", usuario.getRoles().getFirst().getRole())
				.withExpiresAt(this.dataExpiracao())
				.sign(algoritmo);
		}catch(JWTCreationException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Erro ao gerar o token jwt: ");
		}
	}
	
	public String getSubject(String tokenJWT) {
		try {
			var algoritmo =  Algorithm.HMAC256(secret);
			return JWT.require(algoritmo)
					.withIssuer(issuer)
					.build()
					.verify(tokenJWT)
					.getSubject();
		}catch(JWTVerificationException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Token JWT inválido ou expirado");
		}
	}
	
	
	public String recuperarToken(HttpServletRequest request) {
		var token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}else {
			return token.replace("Bearer ","");
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
