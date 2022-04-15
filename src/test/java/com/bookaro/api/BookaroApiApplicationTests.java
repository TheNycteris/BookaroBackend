package com.bookaro.api;


import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.UserRepository;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookaroApiApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@DynamicPropertySource
	  static void postgresqlProperties(DynamicPropertyRegistry registry) {
	    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
	    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
	  }
	  @Container
	  public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11-alpine")
	      .withDatabaseName("integration-tests-db")
	      .withPassword("inmemory")
	      .withUsername("inmemory");
	

	@Test
	void contextLoads() {
		
		List<User> allCars = (List<User>) userRepository.findAll();
	    assert !allCars.isEmpty();
	    assert allCars.get(0).getUsername().equalsIgnoreCase("Pedro");
		
	}

}
