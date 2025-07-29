package br.edu.ifba.inf012.internetBanking.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoForm;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoPagamento;
import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import br.edu.ifba.inf012.internetBanking.services.OperacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
	public OperacaoDto realizarDeposito(@RequestBody OperacaoForm operacao) throws Exception {
		if(new BigDecimal(operacao.valor()).compareTo(new BigDecimal("0.0"))<0) {
			throw new Exception("Valor de deposito não pode ser negativo");
		}
		return this.operacaoService.realizarDeposito(operacao);
	}
	
	@Operation(summary="Realizar Saque", description="Registra o saque realizado por um usuario em sua conta")
	@ApiResponse(responseCode="201", description="Registra um saque")
	@PostMapping("/saque")
	public OperacaoDto realizarSaque(@RequestBody OperacaoForm operacao) throws Exception {
		if(new BigDecimal(operacao.valor()).compareTo(new BigDecimal("0.0"))<0) {
			throw new Exception("Valor de saque não pode ser negativo");
		}
		return this.operacaoService.realizarSaque(operacao);
	}
	
	@Operation(summary="Realizar Pagamento", description="Registra o pagamento realizado por um usuario em sua conta")
	@ApiResponse(responseCode="201", description="Registra um pagamento")
	@PostMapping("/pagamento")
	public OperacaoDto realizarPagamento(@RequestBody OperacaoPagamento operacao) throws Exception {
		if(new BigDecimal(operacao.valor()).compareTo(new BigDecimal("0.0"))<0) {
			throw new Exception("Pagamentos não podem ser dedutivos");
		}
		return this.operacaoService.realizarPagamento(operacao);
	}
	
	@Operation(summary="Listar Operacações", description="Retorna o extrato o usuario(lista das operações)")
	@ApiResponse(responseCode="200", description="Retorna lista de operações")
	@GetMapping("/extrato/{usuarioId}")
	public List<OperacaoDto> extrato(@RequestParam TipoOperacao tipo, @RequestParam(required=false) LocalDate dataInicio, @RequestParam(required=false) LocalDate dataFim, @PathVariable Long usuarioId){
		return this.operacaoService.pegarExtrato(usuarioId, tipo, dataFim, dataFim);
	}
}
