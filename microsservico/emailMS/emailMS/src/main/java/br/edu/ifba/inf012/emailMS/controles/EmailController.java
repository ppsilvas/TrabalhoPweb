package br.edu.ifba.inf012.emailMS.controles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.inf012.emailMS.dtos.EmailForm;
import br.edu.ifba.inf012.emailMS.servicos.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	EmailService emailService;

	public EmailController(EmailService emailService) {
		super();
		this.emailService = emailService;
	}
	
	@PostMapping("/send")
	public ResponseEntity<EmailForm> sendEmail(@RequestBody EmailForm form){
		EmailForm email = this.emailService.sendEmail(form);
		return new ResponseEntity<EmailForm>(email,HttpStatus.CREATED);
	}
}
