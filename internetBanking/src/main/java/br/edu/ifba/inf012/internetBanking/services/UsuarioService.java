package br.edu.ifba.inf012.internetBanking.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioForm;
import br.edu.ifba.inf012.internetBanking.models.Role;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.repositories.RoleRepository;
import br.edu.ifba.inf012.internetBanking.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	private UsuarioRepository usuarioRepository;
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder encoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
	}
	
	public Usuario criarNovoUsuario(UsuarioForm form) {
		if(this.usuarioRepository.existsByCpf(form.cpf()) || this.usuarioRepository.existsByEmail(form.email()))
			throw new IllegalArgumentException("Usuario já está cadastrado");
		List<Role> roles =  this.roleRepository.findAll();
		Usuario novoUsuario = new Usuario(form, roles);
		novoUsuario.setSenha(this.encoder.encode(novoUsuario.getSenha()));
		return this.usuarioRepository.save(novoUsuario);
	}

}
