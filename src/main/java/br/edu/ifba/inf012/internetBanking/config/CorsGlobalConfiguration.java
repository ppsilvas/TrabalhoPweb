package br.edu.ifba.inf012.internetBanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsGlobalConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("api/**")
				.allowedOriginPatterns("http://172.16.7.255")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(true);
			}
		};
	}
}
