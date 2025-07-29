package br.edu.ifba.inf012.internetBanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.internetBanking.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByEmail(String email);
	Usuario findByCpf(String cpf);
	boolean existsByCpf(String cpf);
	boolean existsByEmail(String email);
}
