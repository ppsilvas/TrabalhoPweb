package br.edu.ifba.inf012.internetBanking.dtos.contaCorrente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ContaForm(
		Long id,
		@NotBlank
		@Positive
		int numero) {

}
