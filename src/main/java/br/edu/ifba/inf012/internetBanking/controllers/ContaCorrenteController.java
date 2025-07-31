package br.edu.ifba.inf012.internetBanking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.services.ContaCorrenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins="http://localhost:3000")
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
	@GetMapping("/{id}")
	@Secured(value = {"ROLE_OWNER"})
	public ResponseEntity<ContaDto> buscaConta(@PathVariable Long id) {
		try {
			ContaDto conta = this.ccService.buscaContaCorrentePorUsuario(id);
			return ResponseEntity.status(HttpStatus.OK).body(conta);
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
