package br.edu.ifba.inf012.internetBanking.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.clients.EmailClient;
import br.edu.ifba.inf012.internetBanking.clients.EmailForm;
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

	@Value("${email.adress.mailFrom}")
	private String mailFrom;
	private OperacaoRepository operacaoRepository;
	private ContaCorrenteRepository ccRepository;
	private EmailClient emailClient;
	
	public OperacaoService(OperacaoRepository operacaoRepository, ContaCorrenteRepository ccRepository, EmailClient emailClient) {
		super();
		this.operacaoRepository = operacaoRepository;
		this.ccRepository = ccRepository;
		this.emailClient = emailClient;
	}

	public OperacaoDto realizarDeposito(OperacaoForm operacao) throws Exception,IllegalArgumentException {
		Optional<ContaCorrente> contaCorrenteDeposito = this.ccRepository.findByNumero(operacao.numConta());
		
		if(contaCorrenteDeposito.isEmpty())
			throw new IllegalArgumentException("Conta não existe");
		
		ContaCorrente ccAtualizadaDeposito = contaCorrenteDeposito.get();
		BigDecimal novoSaldo = ccAtualizadaDeposito.getSaldoDecimal().add(new BigDecimal(operacao.valor()));
		ccAtualizadaDeposito.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizadaDeposito);
		
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizadaDeposito));
		
		if(novaOperacao == null)
			throw new Exception("Erro ao realizar o deposito");
		this.emailClient.sendEmail(new EmailForm(null, mailFrom,
				ccAtualizadaDeposito.getUsuario().getEmail(),
				"Depósito realizado",
				"Você recebeu un depósito de R$" + operacao.valor() + ". Saldo atual: R$" + novoSaldo.toString()));
		
		return new OperacaoDto("Deposito realizado com sucesso",novaOperacao.getConta());
	}
	
	public OperacaoDto realizarSaque(OperacaoForm operacao, Long usuarioId) throws Exception,IllegalArgumentException {
		Optional<ContaCorrente> contaCorrenteSaque = this.ccRepository.findByNumero(operacao.numConta());
		
		if(contaCorrenteSaque.isEmpty())
			throw new IllegalArgumentException("Conta não existe");
		
		ContaCorrente ccAtualizadaSaque = contaCorrenteSaque.get();
				
		if(ccAtualizadaSaque.getSaldoDecimal().compareTo(new BigDecimal(operacao.valor()))<=0)
			throw new IllegalArgumentException("Saldo insuficiente");
		
		if(ccAtualizadaSaque.getUsuario().getId() != usuarioId)
			throw new IllegalArgumentException("Operacao inválida, titulares não coincidem");
		
		BigDecimal novoSaldo = ccAtualizadaSaque.getSaldoDecimal().subtract(new BigDecimal(operacao.valor()));
		ccAtualizadaSaque.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizadaSaque);
				
		Operacao novaOperacao =  this.operacaoRepository.save(new Operacao(operacao, ccAtualizadaSaque));
		
		if(novaOperacao == null)
			throw new Exception("Erro ao realizar o saque");
		this.emailClient.sendEmail(new EmailForm(null, mailFrom,
				ccAtualizadaSaque.getUsuario().getEmail(),
				"Saque realizado",
				"Você fez um saque de R$" + operacao.valor() + ". Saldo atual: R$" + novoSaldo.toString()));
				
		return new OperacaoDto("Saque realizado com sucesso", novaOperacao.getConta());
	}
	
	public OperacaoDto realizarPagamento(OperacaoForm operacao, Long usuarioId) throws Exception,IllegalArgumentException {
		Optional<ContaCorrente> contaCorrentePagamento = this.ccRepository.findByNumero(operacao.numConta());
		
		if(contaCorrentePagamento.isEmpty())
			throw new IllegalArgumentException("Conta não existe");
		
		ContaCorrente ccAtualizadaPagamento = contaCorrentePagamento.get();
		if(ccAtualizadaPagamento.getSaldoDecimal().compareTo(new BigDecimal(operacao.valor()))<=0)
			throw new IllegalArgumentException("Saldo insuficiente");
		
		if(ccAtualizadaPagamento.getUsuario().getId() != usuarioId)
			throw new IllegalArgumentException("Operacao inválida, titulares não coincidem");
		
		BigDecimal novoSaldo = ccAtualizadaPagamento.getSaldoDecimal().subtract(new BigDecimal(operacao.valor()));
		ccAtualizadaPagamento.setSaldo(novoSaldo.toString());
		this.ccRepository.save(ccAtualizadaPagamento);
		
		Operacao novaOperacao = this.operacaoRepository.save(new Operacao(operacao, ccAtualizadaPagamento));
		
		if(novaOperacao == null)
			throw new Exception("Erro ao realizar o pagamento");
		this.emailClient.sendEmail(new EmailForm(null, mailFrom,
				ccAtualizadaPagamento.getUsuario().getEmail(),
				"Pagamento realizado",
				"Você fez um pagamento de R$" + operacao.valor() + ". Saldo atual: R$" + novoSaldo.toString()));
		
		return new OperacaoDto("Pagamento realizado com sucesso",novaOperacao.getConta());
	}
	
	public List<OperacaoExtrato> pegarExtrato(Long numConta, FiltroExtratoDto filtro) throws Exception {
		Optional<ContaCorrente> contaDoUsuario = this.ccRepository.findByNumero(numConta);
		
		if(contaDoUsuario.isEmpty())
			throw new Exception("Conta não existe");
		
		ContaCorrente conta = contaDoUsuario.get();
		try {
			if(filtro == null || (filtro.tipo() == null && filtro.dataInicio() == null && filtro.dataFim()== null)) {
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
		}catch(Exception ex) {
			ex.printStackTrace();
			List<OperacaoExtrato> erro = new ArrayList<>();
			erro.add(new OperacaoExtrato(ex.getMessage(), null, null, 0, null, null, null));
			return erro;
		}
	}
}
