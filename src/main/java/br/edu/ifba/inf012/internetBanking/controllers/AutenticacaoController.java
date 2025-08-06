package br.edu.ifba.inf012.internetBanking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.autenticacao.JwtDto;
import br.edu.ifba.inf012.internetBanking.dtos.autenticacao.LoginForm;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.services.JWTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {
	
	private AuthenticationManager manager;
	private JWTokenService tokenService;

	public AutenticacaoController(AuthenticationManager manager,JWTokenService tokenService) {
		super();
		this.manager = manager;
		this.tokenService = tokenService;
	}
	
	@Operation(summary="Realizar Autenticacao", description="Realiza autenticação no banco e retorna um Jwt")
	@ApiResponse(responseCode="200", description="Realiza autenticacao no banco")
	@PostMapping
	public ResponseEntity<JwtDto> efetuarLogin(@RequestBody LoginForm login) {
		var token = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
		var authentication = this.manager.authenticate(token);
		var tokenJwt = tokenService.gerarToken((Usuario)authentication.getPrincipal());
	
		return ResponseEntity.ok(new JwtDto(tokenJwt));
	}
}
