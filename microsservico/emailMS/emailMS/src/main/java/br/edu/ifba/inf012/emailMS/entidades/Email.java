package br.edu.ifba.inf012.emailMS.entidades;

import java.time.LocalDateTime;

import br.edu.ifba.inf012.emailMS.dtos.EmailForm;
import br.edu.ifba.inf012.emailMS.enums.EmailStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "emails")
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mailFrom;
	private String mailTo;
	private String subject;
	private String body;
	private LocalDateTime sendDateEmail;
	@Enumerated(EnumType.STRING)
	private EmailStatus status=EmailStatus.SENT;
	
	public Email() {
		super();
	}

	public Email(Long id, String mailFrom, String mailTo, String subject, String body) {
		super();
		this.id = id;
		this.mailFrom = mailFrom;
		this.mailTo = mailTo;
		this.subject = subject;
		this.body = body;
	}
	
	public Email(EmailForm form) {
		super();
		this.id = form.id();
		this.mailFrom = form.mailFrom();
		this.mailTo = form.mailTo();
		this.subject = form.subject();
		this.body = form.body();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getSendDateEmail() {
		return sendDateEmail;
	}

	public void setSendDateEmail(LocalDateTime sendDateEmail) {
		this.sendDateEmail = sendDateEmail;
	}

	public EmailStatus getStatus() {
		return status;
	}

	public void setStatus(EmailStatus status) {
		this.status = status;
	}

	
	
}
