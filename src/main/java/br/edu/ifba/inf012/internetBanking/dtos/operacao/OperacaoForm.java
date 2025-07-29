package br.edu.ifba.inf012.internetBanking.dtos.operacao;


import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;

public record OperacaoForm(Long id, String valor, Long usuarioId, TipoOperacao tipo) {

}
