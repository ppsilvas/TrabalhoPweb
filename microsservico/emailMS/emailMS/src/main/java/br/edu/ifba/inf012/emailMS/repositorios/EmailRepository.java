package br.edu.ifba.inf012.emailMS.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.inf012.emailMS.entidades.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{

}
