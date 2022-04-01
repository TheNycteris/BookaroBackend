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
import com.bookaro.api.controllers.UserController;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.ClientRepository;
import com.bookaro.api.repositories.EmployeeRepository;
import com.bookaro.api.repositories.SubscriptionRepository;
import com.bookaro.api.repositories.UserRepository;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PruebasBookaro {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserController userController;

	@Test
	void insert() {
		
		//Subscription
		Subscription subscription = new Subscription();
		//subscription.setId(1L);
		subscription.setType("Familiar");
		subscription.setPrice(30);
		
		subscriptionRepository.save(subscription);
		ArrayList<Subscription> subscriptions = (ArrayList<Subscription>) subscriptionRepository.findAll();
		assert !subscriptions.isEmpty();
		assert subscriptions.get(0).getType().equalsIgnoreCase("Familiar");
		
		//Employee
		//Employee employee = new Employee(1L, "prueba", "1234", "trabajador", "Pedro", "Ruiz", "43125340H", "Dirección de prueba", 39, "jefe", 1200);
		//Usuario employee = new Employee(1L, "prueba", "1234", "trabajador", "Pedro", "Ruiz", "43125340H", "Dirección de prueba", 39, "jefe", 1200);
		User employee = new Employee();
		employee.setAddress("dirección de prueba");
		employee.setAge(20);
		employee.setDni("43125340H");
		employee.setName("Pedro");
		employee.setPassword("1234");
		employee.setSurname("ruiz");
		employee.setType("trabajador");
		employee.setUsername("pedro");
		employee.setType("trabajador");
		//employeeRepository.save(employee);
		//userRepository.save(employee);
		userController.saveUsuario(employee);
		
		ArrayList<Employee> employees = (ArrayList<Employee>) employeeRepository.findAll();
		
		for(Employee e: employees) {
			System.out.println(e.getUsername() + " - " + e.getPassword());
		}
		
		assert !employees.isEmpty();
		assert employees.get(0).getUsername().equalsIgnoreCase("pedro");
		
		
		//Client
		//Client client = new Client();
		User client = new Client();
		client.setAddress("Dirección de prueba");
		client.setAge(50);
		client.setDni("43109793L");
		client.setName("Sonia");
		client.setPassword("1234");
		client.setSurname("Garrido");
		client.setType("Cliente");
		client.setUsername("soniaG");
		((Client)client).setSubscription(subscription);
		//clientRepository.save(client);
		userController.saveUsuario(client);
		ArrayList<Client> clients = (ArrayList<Client>) clientRepository.findAll();
		assert !clients.isEmpty();
		assert clients.get(0).getType().equalsIgnoreCase("Cliente");

	}

}
