package br.edu.ifba.inf012.internetBanking.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.services.ContaCorrenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/conta")
public class ContaCorrenteController {
	
	private ContaCorrenteService ccService;

	public ContaCorrenteController(ContaCorrenteService ccService) {
		super();
		this.ccService = ccService;
	}
	
	@Operation(summary="Buscar conta do usuario", description="Busca a conta do usuario cujo id foi enviado pela path")
	@ApiResponse(responseCode="200", description="Buscar conta do usuario")
	@GetMapping("/{usuarioId}")
	public ContaDto buscaContaDoUsuario(@PathVariable Long usuarioId) {
		return this.ccService.buscaContaCorrentePorUsuario(usuarioId);
	}
}
