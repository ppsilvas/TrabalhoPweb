package br.edu.ifba.inf012.internetBanking.dtos.operacao;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;

public record OperacaoDto(String mensagem, ContaDto conta) {
	public OperacaoDto(String mensagem, ContaCorrente conta) {
		this(mensagem, new ContaDto(conta));
	}
}
