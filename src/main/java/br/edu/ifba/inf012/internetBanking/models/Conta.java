package br.edu.ifba.inf012.internetBanking.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "contas")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private int agencia;
    private BigDecimal saldo;
    @ManyToOne
    private Usuario usuario;

    public Conta(Long id, int numero, int agencia, BigDecimal saldo, Usuario usuario) {
        this.id = id;
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
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
        return saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
