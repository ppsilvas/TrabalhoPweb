package br.edu.ifba.inf012.internetBanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsGlobalConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOriginPatterns("http://localhost:3000")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(true);
			}
		};
	}
}
