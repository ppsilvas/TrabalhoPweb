package br.edu.ifba.inf012.internetBanking.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaForm;

@Entity(name = "contas")
public class ContaCorrente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private int agencia;
    private String saldo;
    @ManyToOne
    private Usuario usuario;

    public ContaCorrente() {
		super();
	}

	public ContaCorrente(Long id, int numero, int agencia, String saldo, Usuario usuario) {
        this.id = id;
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public ContaCorrente(ContaForm contaCorrente, Usuario usuario) {
    	this.id = contaCorrente.id();
    	this.numero = contaCorrente.numero();
    	this.agencia = contaCorrente.agencia();
    	this.saldo = contaCorrente.saldo();
    	this.usuario = usuario;
    }

	public Long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public BigDecimal getSaldo() {
        return new BigDecimal(saldo);
    }

    public Usuario getUsuario() {
        return usuario;
    }

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
    
}
