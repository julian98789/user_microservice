package com.user_microservice.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class UserApplication {
	public static void main(String[] args) {

		SpringApplication.run(UserApplication.class, args);
		log.info("La aplicacion User Service ha iniciado correctamente y esta lista para manejar solicitudes.");
	}

}
