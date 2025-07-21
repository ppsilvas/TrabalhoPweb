package br.edu.ifba.inf012.internetBanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.internetBanking.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

}
