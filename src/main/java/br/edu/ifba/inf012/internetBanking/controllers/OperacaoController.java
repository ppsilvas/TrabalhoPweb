package br.edu.ifba.inf012.internetBanking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.FiltroExtratoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoExtrato;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoForm;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;
import br.edu.ifba.inf012.internetBanking.services.OperacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/operacoes")
public class OperacaoController {
	
	private OperacaoService operacaoService;

	public OperacaoController(OperacaoService operacaoService) {
		super();
		this.operacaoService = operacaoService;
	}
	
	@Operation(summary="Realizar Deposito", description="Registra o deposito realizado na conta de um respectivo usuario")
	@ApiResponse(responseCode="201", description="Registra um deposito")
	@PostMapping("/deposito")
	public ResponseEntity<OperacaoDto> realizarDeposito(@RequestBody OperacaoForm operacao) throws Exception {
		try {
			OperacaoDto depositoRealizado = this.operacaoService.realizarDeposito(operacao);
			return ResponseEntity.status(HttpStatus.CREATED).body(depositoRealizado);
		}catch(IllegalArgumentException ex) {
			OperacaoDto erro = new OperacaoDto(ex.getMessage(),new ContaDto(new ContaCorrente()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		}catch(Exception ex) {
			OperacaoDto erro = new OperacaoDto(ex.getMessage(),new ContaDto(new ContaCorrente()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
		}
	}
	
	@Operation(summary="Realizar Saque", description="Registra o saque realizado por um usuario em sua conta")
	@ApiResponse(responseCode="201", description="Registra um saque")
	@PostMapping("/{usuarioId}/saque")
	@Secured(value = {"ROLE_OWNER"})
	public ResponseEntity<OperacaoDto> realizarSaque(@RequestBody OperacaoForm operacao, @PathVariable Long usuarioId) throws Exception {
		try {
			OperacaoDto saqueRealizado = this.operacaoService.realizarSaque(operacao, usuarioId);
			return ResponseEntity.status(HttpStatus.CREATED).body(saqueRealizado);
		}catch(IllegalArgumentException ex) {
			OperacaoDto erro = new OperacaoDto(ex.getMessage(),new ContaDto(new ContaCorrente()));
			if(ex.getMessage().equals("Conta não existe"))
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}catch(Exception ex) {
			OperacaoDto erro = new OperacaoDto(ex.getMessage(),new ContaDto(new ContaCorrente()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
		}
	}
	
	@Operation(summary="Realizar Pagamento", description="Registra o pagamento realizado por um usuario em sua conta")
	@ApiResponse(responseCode="201", description="Registra um pagamento")
	@PostMapping("/{usuarioId}/pagamento")
	@Secured(value = {"ROLE_OWNER"})
	public ResponseEntity<OperacaoDto> realizarPagamento(@RequestBody OperacaoForm operacao, @PathVariable Long usuarioId) throws Exception {
		try{
		OperacaoDto pagamentoRealizado = this.operacaoService.realizarPagamento(operacao, usuarioId);
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoRealizado);
		}catch(IllegalArgumentException ex) {
			OperacaoDto erro = new OperacaoDto(ex.getMessage(),new ContaDto(new ContaCorrente()));
			if(ex.getMessage().equals("Conta não existe"))
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}catch(Exception ex) {
			OperacaoDto erro = new OperacaoDto(ex.getMessage(),new ContaDto(new ContaCorrente()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
		}
	}
	
	@Operation(summary="Listar Operacações", description="Retorna o extrato o usuario(lista das operações)")
	@ApiResponse(responseCode="200", description="Retorna lista de operações")
	@GetMapping("/{numConta}/extrato")
	@Secured(value = {"ROLE_OWNER"})
	public ResponseEntity<List<OperacaoExtrato>> extrato(
			@RequestBody(required=false) @Valid FiltroExtratoDto filtro,
			@PathVariable Long numConta) throws Exception{
		try {	
			List<OperacaoExtrato> extrato = this.operacaoService.pegarExtrato(numConta, filtro);
			if(extrato.isEmpty() || extrato == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(extrato);
		}catch(Exception ex) {
			ex.printStackTrace();
			List<OperacaoExtrato> erro = new ArrayList<>();
			erro.add(new OperacaoExtrato(ex.getMessage(), null, null, 0, null, null, null));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		}
	}
}
