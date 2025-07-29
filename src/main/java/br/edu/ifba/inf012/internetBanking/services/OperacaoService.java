package br.edu.ifba.inf012.internetBanking.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoForm;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoPagamento;
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

	public OperacaoDto realizarDeposito(OperacaoForm operacao) {
		ContaCorrente contaDoUsuario = this.ccRepository.findByUsuarioId(operacao.usuarioId());
		Optional<ContaCorrente> contaCorrente = this.ccRepository.findById(contaDoUsuario.getId());
		if(contaCorrente.isEmpty())
			return null;
		ContaCorrente ccAtualizada = contaCorrente.get();
		BigDecimal novoSaldo = ccAtualizada.getSaldo().add(new BigDecimal(operacao.valor()));
		ccAtualizada.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizada);
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizada));
		return new OperacaoDto(novaOperacao);
	}
	
	public OperacaoDto realizarSaque(OperacaoForm operacao) {
		ContaCorrente contaDoUsuario = this.ccRepository.findByUsuarioId(operacao.usuarioId());
		Optional<ContaCorrente> contaCorrente = this.ccRepository.findById(contaDoUsuario.getId());
		if(contaCorrente.isEmpty())
			return null;
		ContaCorrente ccAtualizada = contaCorrente.get();
		if(ccAtualizada.getSaldo().compareTo(new BigDecimal("0.0"))<0)
			return null;
		BigDecimal novoSaldo = ccAtualizada.getSaldo().subtract(new BigDecimal(operacao.valor()));
		ccAtualizada.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizada);
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizada));
		return new OperacaoDto(novaOperacao);
	}
	
	public OperacaoDto realizarPagamento(OperacaoPagamento operacao) {
		ContaCorrente contaDoUsuario = this.ccRepository.findByUsuarioId(operacao.usuarioId());
		Optional<ContaCorrente> contaCorrente = this.ccRepository.findById(contaDoUsuario.getId());
		if(contaCorrente.isEmpty())
			return null;
		ContaCorrente ccAtualizada = contaCorrente.get();
		if(ccAtualizada.getSaldo().compareTo(new BigDecimal("0.0"))<0)
			return null;
		BigDecimal novoSaldo = ccAtualizada.getSaldo().subtract(new BigDecimal(operacao.valor()));
		ccAtualizada.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizada);
		Operacao novaOperacao = this.operacaoRepository.save(new Operacao(operacao, ccAtualizada));
		return new OperacaoDto(novaOperacao);
	}
	
	public List<OperacaoDto> pegarExtrato(Long usuarioId,TipoOperacao tipo, LocalDate dataInicio, LocalDate dataFim) {
		ContaCorrente contaDoUsuario = this.ccRepository.findByUsuarioId(usuarioId);
		Optional<ContaCorrente> conta = this.ccRepository.findById(contaDoUsuario.getId());
		List<OperacaoDto> extrato = null;
		if(conta.isEmpty())
			return null;
		if(dataInicio != null && dataFim != null) {
			extrato = this.operacaoRepository.findByContaAndTipo(conta.get(),tipo)
				.stream()
				.map(OperacaoDto::new)
				.filter(operacao->operacao.dataHora().isEqual(LocalDateTime.of(dataInicio, LocalTime.of(0, 0))) 
						|| operacao.dataHora().isAfter(LocalDateTime.of(dataInicio, LocalTime.of(0, 0)))
						|| operacao.dataHora().isEqual(LocalDateTime.of(dataFim, LocalTime.of(0, 0)))
						|| operacao.dataHora().isBefore(LocalDateTime.of(dataFim, LocalTime.of(0, 0))))
				.toList();
		}else if(dataInicio != null && dataFim == null){
			extrato = this.operacaoRepository.findByContaAndTipo(conta.get(),tipo)
					.stream()
					.map(OperacaoDto::new)
					.filter(operacao->operacao.dataHora().isEqual(LocalDateTime.of(dataInicio, LocalTime.of(0, 0))) 
							|| operacao.dataHora().isAfter(LocalDateTime.of(dataInicio, LocalTime.of(0, 0))))
					.toList();
		}else {
			extrato = this.operacaoRepository.findByContaAndTipo(conta.get(),tipo)
					.stream()
					.map(OperacaoDto::new)
					.toList();
		}
		if(extrato.isEmpty()) {
			return null;
		}
		return extrato;
	}
}
