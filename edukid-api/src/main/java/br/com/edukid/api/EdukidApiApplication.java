package br.com.edukid.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdukidApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdukidApiApplication.class, args);
	}

}
