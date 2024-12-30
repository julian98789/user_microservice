package com.user_microservice.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {
	private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);
	public static void main(String[] args) {

		SpringApplication.run(UserApplication.class, args);
		logger.info("La aplicacion User Service ha iniciado correctamente y esta lista para manejar solicitudes.");
	}

}
