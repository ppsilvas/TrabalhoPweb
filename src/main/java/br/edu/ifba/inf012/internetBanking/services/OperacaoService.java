package br.edu.ifba.inf012.internetBanking.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.operacao.FiltroExtratoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoDto;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoExtrato;
import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoForm;
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
		Optional<ContaCorrente> contaCorrenteDeposito = this.ccRepository.findById(operacao.contaId());
		
		if(contaCorrenteDeposito.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente ccAtualizadaDeposito = contaCorrenteDeposito.get();
		BigDecimal novoSaldo = ccAtualizadaDeposito.getSaldoDecimal().add(new BigDecimal(operacao.valor()));
		ccAtualizadaDeposito.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizadaDeposito);
		
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizadaDeposito));
		return new OperacaoDto("Deposito realizado com sucesso",novaOperacao.getConta());
	}
	
	public OperacaoDto realizarSaque(OperacaoForm operacao, Long usuarioId) throws Exception {
		Optional<ContaCorrente> contaCorrenteSaque = this.ccRepository.findById(operacao.contaId());
		
		if(contaCorrenteSaque.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente ccAtualizadaSaque = contaCorrenteSaque.get();
		if(ccAtualizadaSaque.getSaldoDecimal().compareTo(new BigDecimal(operacao.valor()))<=0)
			throw new IllegalArgumentException("Saldo insuficiente");
		
		if(ccAtualizadaSaque.getUsuario().getId() != usuarioId)
			throw new IllegalArgumentException("Operacao inválida, titulares não coincidem");
		
		BigDecimal novoSaldo = ccAtualizadaSaque.getSaldoDecimal().subtract(new BigDecimal(operacao.valor()));
		ccAtualizadaSaque.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizadaSaque);
		
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizadaSaque));
		return new OperacaoDto("Saque realizado com sucesso",novaOperacao.getConta());
	}
	
	public OperacaoDto realizarPagamento(OperacaoForm operacao, Long usuarioId) throws Exception {
		Optional<ContaCorrente> contaCorrentePagamento = this.ccRepository.findById(operacao.contaId());
		
		if(contaCorrentePagamento.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente ccAtualizadaPagamento = contaCorrentePagamento.get();
		if(ccAtualizadaPagamento.getSaldoDecimal().compareTo(new BigDecimal(operacao.valor()))<=0)
			throw new IllegalArgumentException("Saldo insuficiente");
		
		if(ccAtualizadaPagamento.getUsuario().getId() != usuarioId)
			throw new IllegalArgumentException("Operacao inválida, titulares não coincidem");
		
		BigDecimal novoSaldo = ccAtualizadaPagamento.getSaldoDecimal().subtract(new BigDecimal(operacao.valor()));
		ccAtualizadaPagamento.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizadaPagamento);
		
		Operacao novaOperacao = this.operacaoRepository.save(new Operacao(operacao, ccAtualizadaPagamento));
		return new OperacaoDto("Pagamento realizado com sucesso",novaOperacao.getConta());
	}
	
	public List<OperacaoExtrato> pegarExtrato(Long id, FiltroExtratoDto filtro) throws Exception {
		Optional<ContaCorrente> contaDoUsuario = this.ccRepository.findById(id);
		
		if(contaDoUsuario.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente conta = contaDoUsuario.get();
		
		if(filtro == null) {
			return this.operacaoRepository.findByConta(conta.getId())
					.stream()
					.map(operacao->new OperacaoExtrato("Extrato: ", operacao.getTipo().toString(), operacao.getConta().getNumero(),operacao.getConta().getAgencia(), operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}else if(filtro.tipo() != null && filtro.dataInicio() == null && filtro.dataFim() == null) {
			return this.operacaoRepository.findByContaETipo(conta.getId(), filtro.tipo().toUpperCase())
					.stream()
					.map(operacao->new OperacaoExtrato("Extrato: ", operacao.getTipo().toString(), operacao.getConta().getNumero(),operacao.getConta().getAgencia(), operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}else if(filtro.tipo() == null && filtro.dataInicio() != null && filtro.dataFim() != null) {
			return this.operacaoRepository.findByContaEntreDatas(conta.getId(), filtro.dataInicio(), filtro.dataFim())
					.stream()
					.map(operacao->new OperacaoExtrato("Extrato: ", operacao.getTipo().toString(), operacao.getConta().getNumero(),operacao.getConta().getAgencia(), operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}else{
			return this.operacaoRepository.findByContaETipoEntreDatas(conta.getId(), filtro.tipo().toUpperCase(), filtro.dataInicio(), filtro.dataFim())
					.stream()
					.map(operacao->new OperacaoExtrato("Extrato: ", operacao.getTipo().toString(), operacao.getConta().getNumero(),operacao.getConta().getAgencia(), operacao.getValorString(), operacao.getDataHora(), operacao.getDescricao()))
					.toList();
		}
	}
}
