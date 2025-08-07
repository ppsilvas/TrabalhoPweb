package br.edu.ifba.inf012.internetBanking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioDto;
import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioForm;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.services.ContaCorrenteService;
import br.edu.ifba.inf012.internetBanking.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	private UsuarioService usuarioService;
	private ContaCorrenteService ccService;

	public UsuarioController(UsuarioService usuarioService, ContaCorrenteService ccService) {
		super();
		this.usuarioService = usuarioService;
		this.ccService = ccService;
	}
	
	@Operation(summary="Cadastrar novo Usuario", description="Cria o novo usuario e uma conta para esse novo usuário")
	@ApiResponse(responseCode="201", description="Cria novo usuario")
	@PostMapping
	public ResponseEntity<UsuarioDto> cadastrarUsuario(@RequestBody UsuarioForm usuario) {
		try {
			Usuario novoUsuario = this.usuarioService.criarNovoUsuario(usuario);
			ContaCorrente novaConta = null;
			while(novaConta == null)
				novaConta = this.ccService.criarNovaContaCorrente(novoUsuario);
			UsuarioDto retorno = new UsuarioDto("Usuário criado com sucesso",novoUsuario, novaConta);
			return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
		}catch(IllegalArgumentException ex) {
			UsuarioDto erro = new UsuarioDto(ex.getMessage(), null, null, null, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}catch(Exception ex) {
			UsuarioDto erro = new UsuarioDto(ex.getMessage(), null, null, null, null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
		}
	}

}
