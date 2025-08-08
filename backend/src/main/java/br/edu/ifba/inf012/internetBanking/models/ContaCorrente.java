package br.edu.ifba.inf012.internetBanking.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;


@Entity(name = "contas")
public class ContaCorrente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique=true)
    private Long numero;
    @Column(nullable=false)
    private int agencia;
    @Column(nullable=false)
    private String saldo;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName="id")
    private Usuario usuario;
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Operacao> operacoes;

    public ContaCorrente() {
		super();
	}

	public ContaCorrente(Long id, Long numero, int agencia, String saldo, Usuario usuario) {
        this.id = id;
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public ContaCorrente(Usuario usuario) {
    	this.numero = 1000 + usuario.getId();
    	this.agencia = 0001;
    	this.saldo = BigDecimal.ZERO.toString();
    	this.usuario = usuario;
    }

	public Long getId() {
        return id;
    }

    public Long getNumero() {
        return numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public BigDecimal getSaldoDecimal() {
        return new BigDecimal(saldo);
    }
    
    public String getSaldoString() {
        return saldo;
    }
    
    public List<Operacao> getOperacoes(){
		return this.operacoes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
    
}
