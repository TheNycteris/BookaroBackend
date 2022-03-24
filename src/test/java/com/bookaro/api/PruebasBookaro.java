package com.bookaro.api;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.UserRepository;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PruebasBookaro {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void test() {

		ArrayList<User> allCars = (ArrayList<User>) userRepository.findAll();

		for(User u: allCars) {
			System.out.println(u.getUsername());
		}
		assert !allCars.isEmpty();
		assert allCars.get(0).getUsername().equalsIgnoreCase("Pedro");

	}

}
