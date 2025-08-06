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
				.allowedOriginPatterns("http://192.168.0.250:5173")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(true);
			}
		};
	}
}
