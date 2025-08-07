package br.edu.ifba.inf012.emailMS.dtos;

import br.edu.ifba.inf012.emailMS.entidades.Email;

public record EmailForm(Long id, String mailFrom, String mailTo, String subject, String body) {

	public EmailForm(Email email) {
		this(email.getId(),email.getMailFrom(),email.getMailTo(), email.getSubject(),email.getBody());
	}
}
