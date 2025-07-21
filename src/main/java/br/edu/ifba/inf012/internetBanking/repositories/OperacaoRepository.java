package br.edu.ifba.inf012.internetBanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.internetBanking.models.Operacao;

public interface OperacaoRepository extends JpaRepository<Operacao, Long>{

}
