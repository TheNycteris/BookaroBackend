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



@SpringBootTest()
class BookaroApiApplicationTests {

	

	@Test
	void contextLoads() {
		
	    assert true;
	    
		
	}

}
