package br.edu.ifba.inf012.internetBanking.dtos.usuario;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;
import br.edu.ifba.inf012.internetBanking.models.Usuario;

public record UsuarioDto(String mensagem, String nome, String cpf, String email, ContaDto conta) {
	public UsuarioDto(String mensagem,Usuario usuario, ContaCorrente conta) {
		this(mensagem, usuario.getNome(), usuario.getCpf(), usuario.getEmail(), new ContaDto(conta));
	}
}
