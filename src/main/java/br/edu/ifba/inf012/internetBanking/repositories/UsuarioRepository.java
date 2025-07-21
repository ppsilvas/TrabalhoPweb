package br.edu.ifba.inf012.internetBanking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.internetBanking.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByCpf(String cpf);
}
