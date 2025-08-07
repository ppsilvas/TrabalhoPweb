package br.edu.ifba.inf012.emailMS.servicos;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.emailMS.dtos.EmailForm;
import br.edu.ifba.inf012.emailMS.entidades.Email;
import br.edu.ifba.inf012.emailMS.enums.EmailStatus;
import br.edu.ifba.inf012.emailMS.repositorios.EmailRepository;

@Service
public class EmailService {

	private EmailRepository emailRepository;
	private JavaMailSender emailSender;
	
	public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
		super();
		this.emailRepository = emailRepository;
		this.emailSender = emailSender;
	}

	public EmailForm sendEmail(EmailForm form) {
		Email data=new Email(form);
		data.setSendDateEmail(LocalDateTime.now());
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(form.mailFrom());
		message.setTo(form.mailTo());
		message.setSubject(form.subject());
		message.setText(form.body());
		
		data.setStatus(EmailStatus.SENT);
		emailSender.send(message);
		emailRepository.save(data);
		return new EmailForm(data);
	}
}
