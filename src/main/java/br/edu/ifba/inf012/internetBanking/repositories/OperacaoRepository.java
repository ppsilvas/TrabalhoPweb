package br.edu.ifba.inf012.internetBanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.internetBanking.enums.TipoOperacao;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;
import br.edu.ifba.inf012.internetBanking.models.Operacao;

public interface OperacaoRepository extends JpaRepository<Operacao, Long>{
	List<Operacao> findByContaAndTipo(ContaCorrente conta, TipoOperacao tipo);
	List<Operacao> findByConta(ContaCorrente conta);
}
