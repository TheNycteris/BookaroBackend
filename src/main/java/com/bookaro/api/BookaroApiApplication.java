package com.bookaro.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** 
 * @author Pedro
 * 
 * Clase que inicia la aplicación.
 *
 */
@SpringBootApplication
public class BookaroApiApplication {

	/**
	 * Metodo Main
	 * @param args Recibe un array de tipo String
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookaroApiApplication.class, args);
	}
	
	/**
	 * Enlace al objeto BCryptPasswordEncoder
	 * @return Retorna un objeto BCryptPasswordEncoder
	 */
	@Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); 
    }	
	

}
