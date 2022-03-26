package com.bookaro.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bookaro.api.models.UserModel;
import com.bookaro.api.repositories.UserRepository;

@SpringBootTest
class BookaroApiApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Test
	void createUserTest() {
		UserModel us = new UserModel();
		us.setId(null);
		us.setUsername("Pau2");
		us.setPassword(encoder.encode("123"));
		us.setRole("USER");
		UserModel returnedUser = userRepository.save(us);
		
		assertTrue(returnedUser.getPassword().equalsIgnoreCase(us.getPassword()));

	}

}
