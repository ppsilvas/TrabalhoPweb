package br.edu.ifba.inf012.internetBanking.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoExtrato;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoForm;
import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;
import br.edu.ifba.inf012.internetBanking.models.Operacao;
import br.edu.ifba.inf012.internetBanking.repositories.ContaCorrenteRepository;
import br.edu.ifba.inf012.internetBanking.repositories.OperacaoRepository;

@Service
public class OperacaoService {

	private OperacaoRepository operacaoRepository;
	private ContaCorrenteRepository ccRepository;
	
	public OperacaoService(OperacaoRepository operacaoRepository, ContaCorrenteRepository ccRepository) {
		super();
		this.operacaoRepository = operacaoRepository;
		this.ccRepository = ccRepository;
	}

	public OperacaoDto realizarDeposito(OperacaoForm operacao) throws Exception {
		Optional<ContaCorrente> contaCorrente = this.ccRepository.findById(operacao.contaId());
		
		if(contaCorrente.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente ccAtualizada = contaCorrente.get();
		BigDecimal novoSaldo = ccAtualizada.getSaldoDecimal().add(new BigDecimal(operacao.valor()));
		ccAtualizada.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizada);
		
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizada));
		return new OperacaoDto("Deposito realizado com sucesso",novaOperacao.getConta());
	}
	
	public OperacaoDto realizarSaque(OperacaoForm operacao, Long usuarioId) throws Exception {
		Optional<ContaCorrente> contaCorrente = this.ccRepository.findById(operacao.contaId());
		
		if(contaCorrente.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente ccAtualizada = contaCorrente.get();
		if(ccAtualizada.getSaldoDecimal().compareTo(new BigDecimal("0.0"))<=0)
			throw new IllegalArgumentException("Saldo insuficiente");
		
		if(ccAtualizada.getUsuario().getId() != usuarioId)
			throw new IllegalArgumentException("Operacao inválida, titulares não coincidem");
		
		BigDecimal novoSaldo = ccAtualizada.getSaldoDecimal().subtract(new BigDecimal(operacao.valor()));
		ccAtualizada.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizada);
		
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizada));
		return new OperacaoDto("Saque realizado com sucesso",novaOperacao.getConta());
	}
	
	public OperacaoDto realizarPagamento(OperacaoForm operacao, Long usuarioId) throws Exception {
		Optional<ContaCorrente> contaCorrente = this.ccRepository.findById(operacao.contaId());
		
		if(contaCorrente.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente ccAtualizada = contaCorrente.get();
		if(ccAtualizada.getSaldoDecimal().compareTo(new BigDecimal("0.0"))<=0)
			throw new IllegalArgumentException("Saldo insuficiente");
		
		if(ccAtualizada.getUsuario().getId() != usuarioId)
			throw new IllegalArgumentException("Operacao inválida, titulares não coincidem");
		
		BigDecimal novoSaldo = ccAtualizada.getSaldoDecimal().subtract(new BigDecimal(operacao.valor()));
		ccAtualizada.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizada);
		
		Operacao novaOperacao = this.operacaoRepository.save(new Operacao(operacao, ccAtualizada));
		return new OperacaoDto("Pagamento realizado com sucesso",novaOperacao.getConta());
	}
	
	public List<OperacaoExtrato> pegarExtrato(Long id,TipoOperacao tipo, LocalDate dataInicio, LocalDate dataFim) throws Exception {
		Optional<ContaCorrente> contaDoUsuario = this.ccRepository.findById(id);
		
		if(contaDoUsuario.isEmpty())
			throw new Exception("Conta não existe");
		
		if(tipo == null) {
			return this.operacaoRepository.findByConta(contaDoUsuario.get())
					.stream()
					.map(operacao->new OperacaoExtrato("Extrato: ",operacao.getTipo().toString(),operacao.getConta().getNumero(),operacao.getConta().getAgencia(),operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}
		
		List<OperacaoExtrato> extrato = null;
		if(dataInicio != null && dataFim != null) {
			extrato = this.operacaoRepository.findByContaAndTipo(contaDoUsuario.get(),tipo)
				.stream()
				.filter(operacao->operacao.getDataHora().isEqual(LocalDateTime.of(dataInicio, LocalTime.of(0, 0)))
						|| operacao.getDataHora().isAfter(LocalDateTime.of(dataInicio, LocalTime.of(0, 0)))
						|| operacao.getDataHora().isEqual(LocalDateTime.of(dataFim, LocalTime.of(0, 0)))
						|| operacao.getDataHora().isBefore(LocalDateTime.of(dataFim, LocalTime.of(0, 0))))
				.map(operacao->new OperacaoExtrato("Extrato: ",operacao.getTipo().toString(),operacao.getConta().getNumero(),operacao.getConta().getAgencia(),operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
				.toList();
		}else if(dataInicio != null && dataFim == null){
			extrato = this.operacaoRepository.findByContaAndTipo(contaDoUsuario.get(),tipo)
					.stream()
					.filter(operacao->operacao.getDataHora().isEqual(LocalDateTime.of(dataInicio, LocalTime.of(0, 0))) 
							|| operacao.getDataHora().isAfter(LocalDateTime.of(dataInicio, LocalTime.of(0, 0))))
					.map(operacao->new OperacaoExtrato("Extrato: ",operacao.getTipo().toString(),operacao.getConta().getNumero(),operacao.getConta().getAgencia(),operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}else {
			extrato = this.operacaoRepository.findByContaAndTipo(contaDoUsuario.get(),tipo)
					.stream()
					.map(operacao->new OperacaoExtrato("Extrato: ",operacao.getTipo().toString(),operacao.getConta().getNumero(),operacao.getConta().getAgencia(),operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}
		
		return extrato;
	}
}
