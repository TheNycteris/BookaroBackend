package com.bookaro.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Test;

import com.bookaro.api.controllers.EmployeeController;
import com.bookaro.api.controllers.UserController;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.ClientRepository;
import com.bookaro.api.repositories.EmployeeRepository;
import com.bookaro.api.repositories.SubscriptionRepository;
import com.bookaro.api.repositories.UserRepository;
import com.bookaro.api.services.ClientService;
import com.bookaro.api.services.EmployeeService;
import com.bookaro.api.services.SubscriptionService;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PruebasBookaro {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeController employeController;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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
		
		
		Subscription subscription1 = new Subscription();
		//subscription.setId(1L);
		subscription1.setType("Básica");
		subscription1.setPrice(10);
		subscriptionRepository.save(subscription1);
		
		//Employee
		//Employee employee = new Employee(1L, "prueba", "1234", "trabajador", "Pedro", "Ruiz", "43125340H", "Dirección de prueba", 39, "jefe", 1200);
		//Usuario employee = new Employee(1L, "prueba", "1234", "trabajador", "Pedro", "Ruiz", "43125340H", "Dirección de prueba", 39, "jefe", 1200);
		
		List<String> rol = new ArrayList();
		rol.add("ROLE_USER");
		
		Employee employee = new Employee();
		
		employee.setAddress("dirección de prueba");
		employee.setAge(20);
		employee.setDni("43555666H");
		employee.setName("Pedro");		
		employee.setPassword("1234");
		employee.setSurname("ruiz");		
		employee.setUsername("pedro");
		((Employee)employee).setPosition("Administrativo");
		((Employee)employee).setSalary(1000);
		employee.setEmail("pedro@prueba.com");		
		employee.setRoles(rol);
		employeeService.add(employee);
		//employeController.addEmployee(employee);
		//userController.create(employee);	
		
		
		ArrayList<Employee> employees = (ArrayList<Employee>) employeeRepository.findAll();
		
		for(Employee e: employees) {
			System.out.println(e.getUsername() + " - " + e.getPassword());
		}
		
		assert !employees.isEmpty();
		assert employees.get(0).getUsername().equalsIgnoreCase("pedro");
		
		
		Client client = new Client();
		client.setUsername("cliente1");
		client.setPassword("1234");
		client.setName("Sonia");
		client.setSurname("garrido");
		client.setDni("43111333L");
		client.setAddress("dirección de prueba");
		client.setEmail("sonia@prueba.com");
		client.setAge(40);
		client.setRoles(rol);
		client.setSubscription(subscription);
		clientService.add(client);
		//clientRepository.save(client);
		//userController.create(client);
		
		
		Client client2 = new Client();
		client2.setUsername("cliente2");
		client2.setPassword("1234");
		client2.setName("Pedro");
		client2.setSurname("Ruiz");
		client2.setDni("43444555H");
		client2.setAddress("dirección");
		client2.setEmail("pedro@prueba.com");
		client2.setAge(40);
		client2.setRoles(rol);
		client2.setSubscription(subscription1);
		clientService.add(client2);
		
		List<Client> todos =  (List<Client>) clientRepository.findAll();/*new ArrayList();*/
		//todos.add(client);
		
		for (Client c: todos) {
			if (c.getSubscription().getType().equalsIgnoreCase("Familiar")) {
				System.out.println(c.getUsername());
			}
		}
		
	
		
		
		
		
	}

}
