package br.edu.ifba.inf012.internetBanking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long>{
	Optional<ContaCorrente> findByUsuarioId(Long usuarioId);
	Optional<ContaCorrente> findByNumero(Long numConta);
}
