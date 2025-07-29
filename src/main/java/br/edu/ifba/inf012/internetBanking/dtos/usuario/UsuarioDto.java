package br.edu.ifba.inf012.internetBanking.dtos.usuario;

import br.edu.ifba.inf012.internetBanking.models.Usuario;

public record UsuarioDto(String nome, String cpf, String email) {
	public UsuarioDto(Usuario usuario) {
		this(usuario.getNome(), usuario.getCpf(), usuario.getEmail());
	}
}
