package br.edu.ifba.inf012.internetBanking.dtos.operacao;


public record OperacaoPagamento(Long id, String valor, Long usuarioId, String descricao) {

}
