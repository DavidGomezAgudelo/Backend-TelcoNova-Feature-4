package com.Fabrica.TelcoNova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(scanBasePackages = "com.Fabrica.TelcoNova")
@EntityScan("com.Fabrica.TelcoNova.model") // <-- Importante
@EnableJpaRepositories("com.Fabrica.TelcoNova.repository")

public class TelcoNovaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelcoNovaApplication.class, args);
	}

}
