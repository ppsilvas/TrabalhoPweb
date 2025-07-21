package br.edu.ifba.inf012.internetBanking.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario criarNovoUsuario(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		return this.usuarioRepository.findById(id)
				.map(usuario->new Usuario(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getSenha(), usuario.getDataCadastro()))
				.orElse(null);
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		return this.usuarioRepository.findByEmail(email)
				.map(usuario->new Usuario(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getSenha(), usuario.getDataCadastro()))
				.orElse(null);
	}
	
	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Optional<Usuario> usuarioAtual = this.usuarioRepository.findById(id);
		if(usuarioAtual.isEmpty()) {
			return null;
		}
		Usuario usuarioAtualizado = usuarioAtual.get();
		usuarioAtualizado.setEmail(usuario.getEmail());
		usuarioAtualizado.setSenha(usuario.getSenha());
		
		return usuarioAtualizado;
	}
	
	public Usuario deletarUsuario(Long id) {
		Optional<Usuario> usuarioAtual = this.usuarioRepository.findById(id);
		if(usuarioAtual.isEmpty())
			return null;
		Usuario usuarioDeletado = usuarioAtual.get();
		this.usuarioRepository.delete(usuarioDeletado);
		return usuarioDeletado;
	}
}
