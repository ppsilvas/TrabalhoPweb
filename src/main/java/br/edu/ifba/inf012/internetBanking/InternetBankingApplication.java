package br.edu.ifba.inf012.internetBanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class InternetBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingApplication.class, args);
	}

}
