package br.edu.ifba.inf012.internetBanking.dtos.operacao;

import java.time.LocalDateTime;

public record OperacaoExtrato(String mensagem, String tipo,int numero, int agencia, String valor, LocalDateTime dataOperacao, String descricao) {

}
