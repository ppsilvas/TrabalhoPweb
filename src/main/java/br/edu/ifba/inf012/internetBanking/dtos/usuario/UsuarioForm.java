package br.edu.ifba.inf012.internetBanking.dtos.usuario;

import java.time.LocalDate;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaForm;

public record UsuarioForm(Long id, String nome, String cpf, String email, String senha, LocalDate dataCadastro, ContaForm conta) {

}
