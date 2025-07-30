package br.edu.ifba.inf012.internetBanking.dtos.contaCorrente;

import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;

public record ContaDto(Long id, int numero, int agencia, String saldo) {
	public ContaDto(ContaCorrente conta) {
		this(conta.getId(), conta.getNumero(), conta.getAgencia(), conta.getSaldoString());
	}
}
