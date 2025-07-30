package br.edu.ifba.inf012.internetBanking.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	@Column(nullable=false)
    private String nome;
    @Column(nullable=false,unique=true)
    private String cpf;
    @Column(nullable=false,unique=true)
    private String email;
    @Column(nullable=false)
    private String senha;
    private LocalDateTime dataCadastro;
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private ContaCorrente conta;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name="usuario_roles",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<Role>();

    public Usuario() {
		super();
	}

	public Usuario(Long id, String nome, String cpf, String email, String senha, LocalDateTime dataCadastro, List<Role> roles) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.roles = roles;
    }

    public Usuario(UsuarioForm usuario, Collection<? extends GrantedAuthority> roles) {
    	this.id = usuario.id();
    	this.nome = usuario.nome();
    	this.cpf = usuario.cpf();
    	this.email = usuario.email();
    	this.senha = usuario.senha();
    	this.dataCadastro = LocalDateTime.now();
    	this.roles = roles.stream().map(role->(Role)role).toList();
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
	
	public ContaCorrente getConta() {
		return conta;
	}

	public List<Role> getRoles() {
		return roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setRoles(Collection<? extends GrantedAuthority>roles) {
		this.roles =  roles.stream().map(role->(Role)role).toList();
	}
    
}
