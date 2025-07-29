package br.edu.ifba.inf012.internetBanking.services;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.usuario.UsuarioForm;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario criarNovoUsuario(UsuarioForm usuario) {
		if(this.usuarioRepository.existsByCpf(usuario.cpf()) || this.usuarioRepository.existsByEmail(usuario.email()))
			throw new RuntimeException("Usuario já está cadastrado");
		Usuario novoUsuario = this.usuarioRepository.save(new Usuario(usuario));
		return novoUsuario;
	}

}
