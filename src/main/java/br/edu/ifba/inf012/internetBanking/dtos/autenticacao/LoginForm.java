package br.edu.ifba.inf012.internetBanking.dtos.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
		@NotBlank
		String email,
		@NotBlank
		String senha) {

}
