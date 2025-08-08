package br.edu.ifba.inf012.internetBanking.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("emailms")
public interface EmailClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/email/send")
	public ResponseEntity<EmailForm> sendEmail(@RequestBody EmailForm form);
	
}
