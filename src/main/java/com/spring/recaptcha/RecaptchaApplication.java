package com.spring.recaptcha;

import kong.unirest.JacksonObjectMapper;
import kong.unirest.Unirest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecaptchaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> Unirest.config().setObjectMapper(new JacksonObjectMapper());
	}

}
