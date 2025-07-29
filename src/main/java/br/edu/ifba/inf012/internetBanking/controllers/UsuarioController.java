package br.edu.ifba.inf012.internetBanking.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioDto;
import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioForm;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.services.ContaCorrenteService;
import br.edu.ifba.inf012.internetBanking.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
	public UsuarioDto cadastrarUsuario(@RequestBody UsuarioForm usuario) {
		Usuario novoUsuario = this.usuarioService.criarNovoUsuario(usuario);
		this.ccService.criarNovaContaCorrente(usuario.conta(), novoUsuario);
		return new UsuarioDto(novoUsuario);
	}

}
