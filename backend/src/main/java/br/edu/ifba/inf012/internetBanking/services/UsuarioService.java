package br.edu.ifba.inf012.internetBanking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.clients.EmailClient;
import br.edu.ifba.inf012.internetBanking.clients.EmailForm;
import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioForm;
import br.edu.ifba.inf012.internetBanking.models.Role;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.repositories.RoleRepository;
import br.edu.ifba.inf012.internetBanking.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Value("${email.adress.mailFrom}")
	private String mailFrom;
	private UsuarioRepository usuarioRepository;
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder encoder;
	
	private EmailClient emailClient;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, RoleRepository roleRepository, EmailClient emailClient) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.emailClient = emailClient;
	}
	
	public Usuario criarNovoUsuario(UsuarioForm form) throws Exception, IllegalArgumentException {
		if(this.usuarioRepository.existsByCpf(form.cpf()) || this.usuarioRepository.existsByEmail(form.email()))
			throw new IllegalArgumentException("Usuario já está cadastrado");
		List<Role> roles =  this.roleRepository.findAll();
		Usuario novoUsuario = new Usuario(form, roles);
		novoUsuario.setSenha(this.encoder.encode(novoUsuario.getSenha()));
		Usuario usuarioCriado = this.usuarioRepository.save(novoUsuario);
		
		if(usuarioCriado == null)
			throw new Exception("Erro ao criar o usuario");
		this.emailClient.sendEmail(new EmailForm(null, mailFrom, usuarioCriado.getEmail(),
				"Bem-vindo ao PWebBank!", "O seu cadastro foi realizado com sucesso!"));
		
		return usuarioCriado;
	}

}
