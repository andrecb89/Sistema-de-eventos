package com.demo.Sistema.de.Eventos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SistemaDeEventosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeEventosApplication.class, args);
	}

}
