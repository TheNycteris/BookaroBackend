package com.bookaro.api;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.bookaro.api.controllers.EmployeeController;
import com.bookaro.api.controllers.SubscriptionController;
import com.bookaro.api.controllers.UserController;
import com.bookaro.api.models.Book;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.Order;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.BookRepository;
import com.bookaro.api.repositories.ClientRepository;
import com.bookaro.api.repositories.EmployeeRepository;
import com.bookaro.api.repositories.OrderRepository;
import com.bookaro.api.repositories.SubscriptionRepository;
import com.bookaro.api.repositories.UserRepository;
import com.bookaro.api.services.BookService;
import com.bookaro.api.services.ClientService;
import com.bookaro.api.services.EmployeeService;
import com.bookaro.api.services.SubscriptionService;
import com.bookaro.api.services.UserService;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PruebasBookaro {
	
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
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeController employeController;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private SubscriptionController subscriptionController;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@BeforeAll
	  static void runOnceBeforeAllTests() {
	    System.out.println("Empiezas las pruebas de la Base de datos");	   
	}
	
	void crearRegistros() {		

		/**
		 * ************ Subscription ************		
		 */		
		// subscription1
		Subscription subscription1 = new Subscription();		
		subscription1.setType("Familiar");
		subscription1.setPrice(30);		
		subscriptionRepository.save(subscription1);

		// subscription2
		Subscription subscription2 = new Subscription();		
		subscription2.setType("Básica");
		subscription2.setPrice(10);
		subscriptionRepository.save(subscription2);

		
		/**
		 * ************ Users/employee ************		
		 */

		// Creamos un empleado
		Employee employee = new Employee();		
		employee.setAddress("dirección de prueba");
		employee.setAge(20);
		employee.setDni("43555666H");
		employee.setName("Pedro");		
		employee.setPassword("1234");
		employee.setSurname("ruiz");		
		employee.setUsername("pedro");
		employee.setPosition("Administrativo");
		employee.setSalary(1000);
		employee.setEmail("pedro@bookaro.com");		
		employee.setRole("ROLE_MOD");
		employeeService.add(employee);
		

		// Creamos un usuario admin con los datos básicos
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("admin");	
		//admin.setEmail("admin@bookaro.com");						
		admin.setRole("ROLE_ADMIN");
		userService.create(admin);
		
		/**
		 * ************ Clients ************		
		 */
		// Creamos client1
		Client client1 = new Client();
		client1.setUsername("cliente1");
		client1.setPassword("1234");
		client1.setName("name1");
		client1.setSurname("surname1");
		client1.setDni("43111333L");
		client1.setAddress("C/ dirección de prueba1");
		client1.setEmail("cliente1@bookaro.com");
		client1.setAge(40);		
		client1.setRole("ROLE_USER");
		client1.setSubscription(subscription1);
		clientService.add(client1);	

		/*// Creamos client2
		Client client2 = new Client();
		client2.setUsername("cliente2");
		client2.setPassword("1234");
		client2.setName("name2");
		client2.setSurname("surname2");
		client2.setDni("43444555H");
		client2.setAddress("C/ dirección de prueba2");
		client2.setEmail("cliente2@bookaro.com");
		client2.setAge(40);		
		client2.setRole("ROLE_USER");
		client2.setSubscription(subscription2);		
		client2.setOrder(order1);
		clientService.add(client2);	*/	
		
		
		/**
		 * ************ Order ************		
		 */
		// Creamos una Order
		Order order1 = new Order();
		order1.setStartDate(new Date());
		order1.setActive(true);
		//order1.setClient(client1);	
		orderRepository.save(order1);
		
		// Creamos una Order
		Order order2 = new Order();
		order2.setStartDate(new Date());
		order2.setActive(true);
		//order2.setClient(client2);	
		
		orderRepository.save(order2);
		
		
		Client client2 = new Client();
		client2.setUsername("cliente2");
		client2.setPassword("1234");
		client2.setName("name2");
		client2.setSurname("surname2");
		client2.setDni("43444555H");
		client2.setAddress("C/ dirección de prueba2");
		client2.setEmail("cliente2@bookaro.com");
		client2.setAge(40);		
		client2.setRole("ROLE_USER");
		client2.setSubscription(subscription2);		
		client2.setOrder(order1);
		clientService.add(client2);
		
		
		/**
		 * ************ Book ************		
		 */
		// Cremos book1
		Book book1 = new Book();
		book1.setName("libro1");
		book1.setAuthor("author1");
		book1.setIsbn("isbn1");
		book1.setCategory("category1");
		book1.setEditorial("editorial1");
		book1.setSynopsis("synopsis1");
		book1.setOrderBook(order1);
		bookService.add(book1);
		
		// Cremos book1
		Book book2 = new Book();
		book2.setName("libro2");
		book2.setAuthor("author2");
		book2.setIsbn("isbn2");
		book2.setCategory("category2");
		book2.setEditorial("editorial2");
		book2.setSynopsis("synopsis2");
		book2.setOrderBook(order2);
		bookService.add(book2);		
	}

	
	
	@BeforeEach
	void prueba() {
		System.out.println("Borrado BD");
		userRepository.deleteAll();
		subscriptionRepository.deleteAll();
		bookRepository.deleteAll();
	}
	
	
	@AfterEach
	void ultima() {
		System.out.println("Prueba de ejecución despues");		
	}
	
	/*@Test
	void testClient() {
		
	}*/
	
	
	
	@Test
	void testBD() {		
		
		// Creamos todos los registros
		crearRegistros();
		
		// Obtenemos todos los datos de la BD (Subscription, Client, Employee, User, Book)
		ArrayList<Subscription> subscriptions = (ArrayList<Subscription>) subscriptionRepository.findAll();		
		List<User> users = (List<User>) userRepository.findAll();
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		List<Client> clients = (List<Client>) clientRepository.findAll();
		List<Book> books = (List<Book>) bookRepository.findAll();
		Optional<Subscription> s2 = subscriptionService.findById(2L);	
		
		
		
		
		// ******************** COMPROBACIONES ******************** //
		
		// ************* Users ************ //		
		// Comprobamos si la lista está vacía
		assert !users.isEmpty();
		// Comprobamos si miden lo mismo la lista de users con el conteo directo a la BD
		assertEquals(users.size(), userRepository.count());		
		assert users.size() != employees.size();
		
		//Actualizamos un usuario
		User user = users.get(1);
		user.setEmail("adminActualizado@bookaro.es");
		//userService.update(user);
		userRepository.save(user);
		
		//Comprobamos que se ha actualizado correctamente.
		//assert userService.findByUsername("admin").get().getEmail().equals("adminActualizado@bookaro.es");
		assert userRepository.findByUsername("admin").getEmail().equals("adminActualizado@bookaro.es");
		
		 
		// * ************ Subscription ************//		
		// Miramos si la lista está vacía
		assert !subscriptions.isEmpty();
		
		// Comprobamos que el primer valor de la lista coincide con el tipo de subscripción "Familiar"
		assert subscriptions.get(0).getType().equalsIgnoreCase("Familiar");
		
		// Comprobamos que la segunda subscripción tiene un precio de "10"
		assert s2.get().getPrice() == 10;		
		// Actualizamos el precio
		Subscription s2Actualizada = subscriptions.get(1);
		s2Actualizada.setPrice(20);		
		subscriptionService.update(s2Actualizada);
		// Comprobamos que se ha actualizado en la base de datos	
		s2 = subscriptionService.findById(2L);
		assert s2.get().getPrice() == 20;
		
		// Comprobamos que no podemos introducir una subscripción con el mismo "id"		
		// Debe saltar una excepción del tipo "JpaSystemException"		 
		JpaSystemException thrown = Assertions.assertThrows(JpaSystemException.class, () -> {
			Subscription subscription3 = new Subscription();
			subscription3.setId(1L);
			subscription3.setType("Básica");
			subscription3.setPrice(10);			
			subscriptionRepository.save(subscription3);
		}, "JpaSystemException was expected");		

		assertEquals("A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: "
				+ "com.bookaro.api.models.Subscription.allClients; nested exception is org.hibernate.HibernateException: "
				+ "A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: "
				+ "com.bookaro.api.models.Subscription.allClients", thrown.getMessage());
		
		
		// * ************ Employee ************//		
		assert !employees.isEmpty();
		assert employees.get(0).getUsername().equalsIgnoreCase("pedro");
		assertEquals(employees.get(0).getAddress(), employeeRepository.findById(1L).get().getAddress());
		
			
		
		DataIntegrityViolationException thrown1 = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			Subscription subscription1 = new Subscription();		
			subscription1.setType("Familiar");
			subscription1.setPrice(30);		
			subscriptionRepository.save(subscription1);
			
			
			// Creamos un cliente
			Client client = new Client();
			client.setUsername("cliente1");
			client.setPassword("1234");
			client.setName("Sonia");
			client.setSurname("garrido");
			client.setDni("43111333L");
			client.setAddress("dirección de prueba");
			client.setEmail("cliente1@bookaro.com");
			client.setAge(40);
			//client.setRoles(rol);
			//client.setRole(rol.get(0));
			client.setRole("ROLE_USER");
			//client.setSubscription(subscription1);
			clientService.add(client);	
			
			
		}, "DataIntegrityViolationException was expected");
		
		assertEquals("could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement", thrown1.getMessage());
		System.out.println("MENSAJEeeee: " + thrown1.getMessage());
		
		//assertEquals("Detail: Ya existe la llave (email)=(cliente1@bookaro.com).", thrown1.getMessage());
		
		
		
		
		
		
		
		
		
		 
	}
	
	@AfterAll
	static void runOnceAfterAllTests() {
	  System.out.println("@AfterAll executed");	  
	}

}
