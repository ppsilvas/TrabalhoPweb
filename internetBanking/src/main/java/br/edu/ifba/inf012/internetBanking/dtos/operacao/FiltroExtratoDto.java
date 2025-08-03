package br.edu.ifba.inf012.internetBanking.dtos.operacao;

import java.time.LocalDateTime;

import br.edu.ifba.inf012.internetBanking.annotations.datas.DatasConsistentes;
import br.edu.ifba.inf012.internetBanking.annotations.enums.ValidEnumValue;
import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import jakarta.validation.constraints.PastOrPresent;

@DatasConsistentes(dataFinal = "dataFim", dataInicial = "dataInicio", message="Erro: A data de inicial do período precisa ser anterior à data de final.")
public record FiltroExtratoDto(
		@ValidEnumValue(enumClass = TipoOperacao.class, ignoreCase = true)
		String tipo,
		@PastOrPresent
		LocalDateTime dataInicio,
		@PastOrPresent
		LocalDateTime dataFim) {

}
