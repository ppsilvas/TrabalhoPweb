package br.edu.ifba.inf012.internetBanking.dtos.operacao;

import java.time.LocalDateTime;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import br.edu.ifba.inf012.internetBanking.models.Operacao;

public record OperacaoDto(Long id, TipoOperacao tipo, String valor, LocalDateTime dataHora, ContaDto conta) {
	public OperacaoDto(Operacao operacao) {
		this(operacao.getId(),operacao.getTipo(),operacao.getValorString(),operacao.getDataHora(),new ContaDto(operacao.getConta()));
	}
}
