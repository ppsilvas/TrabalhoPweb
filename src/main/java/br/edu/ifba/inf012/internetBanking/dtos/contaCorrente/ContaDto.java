package br.edu.ifba.inf012.internetBanking.dtos.contaCorrente;

import java.math.BigDecimal;

import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioDto;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;

public record ContaDto(Long id, int numero, int agencia, BigDecimal saldo, UsuarioDto usuario) {
	public ContaDto(ContaCorrente conta) {
		this(conta.getId(), conta.getNumero(), conta.getAgencia(), conta.getSaldo(), new UsuarioDto(conta.getUsuario()));
	}
}
