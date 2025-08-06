package br.edu.ifba.inf012.internetBanking.dtos.operacao;


import br.edu.ifba.inf012.internetBanking.annotations.enums.ValidEnumValue;
import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OperacaoForm(
		Long id,
		@NotBlank
		@DecimalMin(value = "0.01")
		String valor,
		@NotBlank
		@Positive
		Long numConta,
		@NotBlank
		@ValidEnumValue(enumClass = TipoOperacao.class, ignoreCase = true)
		TipoOperacao tipo,
		String descricao) {

}
