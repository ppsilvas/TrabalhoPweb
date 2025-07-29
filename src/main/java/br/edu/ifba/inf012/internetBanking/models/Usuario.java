package br.edu.ifba.inf012.internetBanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioForm;

@Entity(name="usuarios")
public class Usuario implements UserDetails{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique=true)
    private String cpf;
    @Column(unique=true)
    private String email;
    private String senha;
    private LocalDate dataCadastro;

    public Usuario() {
		super();
	}

	public Usuario(Long id, String nome, String cpf, String email, String senha, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    public Usuario(UsuarioForm usuario) {
    	this.id = usuario.id();
    	this.nome = usuario.nome();
    	this.cpf = usuario.cpf();
    	this.email = usuario.email();
    	this.senha = usuario.senha();
    	this.dataCadastro = usuario.dataCadastro();
    }

	public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
    
}
