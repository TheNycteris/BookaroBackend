package com.bookaro.api;


import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.BookRepository;
import com.bookaro.api.repositories.ClientRepository;
import com.bookaro.api.repositories.EmployeeRepository;
import com.bookaro.api.repositories.OrderRepository;
import com.bookaro.api.repositories.SubscriptionRepository;
import com.bookaro.api.repositories.UserRepository;



@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookaroApiApplicationTests {
	
	// Enlaces
	@Autowired 
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private static UserRepository deleteRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;



	@Test
	void insertUser() { 
		
		userRepository.deleteAll();
		
		// Creamos un usuario admin con los datos básicos
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));	
		admin.setEmail("admin@bookaro.com");						
		admin.setRole("ROLE_ADMIN");		
		userRepository.save(admin);

		// Creamos un empleado
		Employee employee = new Employee();		
		employee.setAddress("dirección de prueba");
		employee.setAge(20);
		employee.setDni("43555666H");
		employee.setName("Pedro");		
		employee.setPassword(passwordEncoder.encode("1234"));
		employee.setSurname("ruiz");		
		employee.setUsername("pedro");
		employee.setPosition("Administrativo");
		employee.setSalary(1000);
		employee.setEmail("pedro@bookaro.com");		
		employee.setRole("ROLE_MOD");		
		employeeRepository.save(employee);

		// Creamos client1
		Client client1 = new Client();
		client1.setUsername("cliente1");
		client1.setPassword(passwordEncoder.encode("1234"));
		client1.setName("name1");
		client1.setSurname("surname1");
		client1.setDni("43111333L");
		client1.setAddress("C/ dirección de prueba1");
		client1.setEmail("cliente1@bookaro.com");
		client1.setAge(40);		
		client1.setRole("ROLE_USER");
		//client1.setSubscription(subscription1);			
		clientRepository.save(client1);
		
		// Creamos client2
		Client client2 = new Client();
		client2.setUsername("cliente2");
		client2.setPassword(passwordEncoder.encode("1234"));
		client2.setName("name2");
		client2.setSurname("surname2");
		client2.setDni("43444555H");
		client2.setAddress("C/ dirección de prueba2");
		client2.setEmail("cliente2@bookaro.com");
		client2.setAge(40);		
		client2.setRole("ROLE_USER");
		//client2.setSubscription(subscription2);		
		//client2.setOrder(order1);		
		clientRepository.save(client2);
		
		List<User> users = (List<User>) userRepository.findAll();
		
		assert !users.isEmpty();

	}

}
