package br.edu.ifba.inf012.internetBanking.models;

import br.edu.ifba.inf012.internetBanking.dtos.operacao.OperacaoForm;
import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "operacoes")
public class Operacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TipoOperacao tipo;
    @Column(nullable=false)
    private String valor;
    private LocalDateTime dataHora;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaCorrente conta;

    public Operacao() {
		super();
	}

	public Operacao(Long id, TipoOperacao tipo, String valor, LocalDateTime dataHora, String descricao, ContaCorrente conta) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.conta = conta;
    }

	public Operacao(OperacaoForm operacao, ContaCorrente conta) {
		this.id = operacao.id();
    	this.tipo = operacao.tipo();
    	this.valor = operacao.valor();
    	this.dataHora = LocalDateTime.now();
    	this.descricao = operacao.descricao();
    	this.conta = conta;
    }

	public Long getId() {
        return id;
    }

    public TipoOperacao getTipo() {
        return tipo;
    }

    public BigDecimal getValorDecimal() {
        return new BigDecimal(valor);
    }
    
    public String getValorString() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public ContaCorrente getConta() {
        return conta;
    }
}
