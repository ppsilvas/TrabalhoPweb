package br.edu.ifba.inf012.internetBanking.models;

import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "operacoes")
public class Operacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoOperacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String descricao;
    @OneToMany
    private Conta conta;

    public Operacao(Long id, TipoOperacao tipo, BigDecimal valor, LocalDateTime dataHora, String descricao, Conta conta) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }

    public TipoOperacao getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public Conta getConta() {
        return conta;
    }
}
