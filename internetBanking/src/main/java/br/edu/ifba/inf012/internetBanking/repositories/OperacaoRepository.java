package br.edu.ifba.inf012.internetBanking.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifba.inf012.internetBanking.models.Operacao;

public interface OperacaoRepository extends JpaRepository<Operacao, Long>{
	@Query(
		value="SELECT o.* FROM operacoes AS o "
			+ "INNER JOIN contas AS c "
			+ "ON (c.id = o.conta_id) "
			+ "WHERE o.conta_id = :conta "
			+ "AND o.tipo = :tipo",
		nativeQuery = true
	)
	List<Operacao> findByContaETipo(@Param("conta") Long conta,@Param("tipo") String tipo);
	
	@Query(
			value="SELECT o.* FROM operacoes AS o "
				+ "INNER JOIN contas AS c "
				+ "ON (c.id = o.conta_id) "
				+ "WHERE o.conta_id = :conta "
				+ "AND DATE(o.data_hora) BETWEEN :inicio AND :fim",
			nativeQuery = true
	)
	List<Operacao> findByContaEntreDatas(@Param("conta") Long conta,@Param("inicio") LocalDateTime inicio,@Param("fim") LocalDateTime fim);
	
	@Query(
			value="SELECT o.* FROM operacoes AS o "
				+ "INNER JOIN contas AS c "
				+ "ON (c.id = o.conta_id) "
				+ "WHERE o.conta_id = :conta "
				+ "AND o.tipo = :tipo "
				+ "AND DATE(o.data_hora) BETWEEN :inicio AND :fim",
			nativeQuery = true
	)
	List<Operacao> findByContaETipoEntreDatas(@Param("conta") Long conta,@Param("tipo") String tipo,@Param("inicio") LocalDateTime inicio,@Param("fim") LocalDateTime fim);
	
	@Query(
			value="SELECT o.* FROM operacoes AS o "
				+ "INNER JOIN contas AS c "
				+ "ON (c.id = o.conta_id) "
				+ "WHERE o.conta_id = :conta",
			nativeQuery = true
	)
	List<Operacao> findByConta(@Param("conta") Long conta);
}
