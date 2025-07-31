package br.edu.ifba.inf012.internetBanking.dtos.usuario;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaForm;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioForm(
		Long id,
		@NotBlank
		@Pattern(regexp = "^[\\p{L} '-]+$", message="O nome só pode ter letras maiusculas e minusculas, espaço e acentos.")
		String nome,
		@NotBlank
		@Size(min=14, max=14)
		String cpf,
		@NotBlank
		@Email
		String email,
		@NotBlank
		@Pattern(regexp = "^[a-zA-z0-9.!?_-&*%@$#|]+$", message="A senha deve ter letra, números e simbolos.")
		@Size.List(value = {
				@Size(min=8, message = "Deve ter no mínimo 8 caracteres"),
				@Size(max=50, message = "Deve ter no máximo 50 caracteres")
		})
		String senha,
		@NotNull
		ContaForm conta
) {}
