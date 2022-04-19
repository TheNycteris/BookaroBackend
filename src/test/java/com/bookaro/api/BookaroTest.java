package com.bookaro.api;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.junit.jupiter.Testcontainers;
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
import static org.junit.Assert.assertEquals;

/**
 * @author Pedro<br>
 * Clase que realizara test sobre la BD, tales como:
 * <li>test1 inserccion de registros</li>
 * <li>test2 actualizacion de registros</li>
 * <li>test3 borrado de registros</li>
 *
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BookaroTest {
	
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
	
	
	
	static List<User> users = new ArrayList();
	static List<Client> clients = new ArrayList();
	static List<Employee> employees = new ArrayList();
	static List<Order> orders = new ArrayList();
	static List<Book> books = new ArrayList();
	static List<Subscription> subscriptions = new ArrayList();
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	/**
	 * Método para insertar todos los registros en la BD
	 */
	@Test
	void test1() {
		System.out.println("*************************** TEST INSERT ***************************");
		
		/**
		 * ************ Subscription ************		
		 */	
		System.out.println("Insertamos subscripción1");
		// subscription1
		Subscription subscription1 = new Subscription();		
		subscription1.setType("Familiar");
		subscription1.setPrice(30);		
		subscriptionRepository.save(subscription1);

		System.out.println("Insertamos subscripción2");
		// subscription2
		Subscription subscription2 = new Subscription();		
		subscription2.setType("Básica");
		subscription2.setPrice(10);
		subscriptionRepository.save(subscription2);
		
		
		/**
		 * ************ Order ************		
		 */
		System.out.println("Insertamos Order1");
		// Creamos una Order
		Order order1 = new Order();
		order1.setStartDate(new Date());
		order1.setActive(true);		
		orderRepository.save(order1);
		
		System.out.println("Insertamos Order2");
		// Creamos una Order
		Order order2 = new Order();
		order2.setStartDate(new Date());
		order2.setActive(true);				
		orderRepository.save(order2);

		
		/**
		 * ************ Users/employee ************		
		 */

		System.out.println("Insertamos employee");
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
		

		// Creamos un usuario admin con los datos básicos
		System.out.println("Insertamos user admin");
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));	
		admin.setEmail("admin@bookaro.com");						
		admin.setRole("ROLE_ADMIN");		
		userRepository.save(admin);
		
		/**
		 * ************ Clients ************		
		 */
		// Creamos client1
		System.out.println("Insertamos cliente1");
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
		client1.setSubscription(subscription1);		
		client1.setOrder(order2);
		clientRepository.save(client1);			
		
		
		// Creamos client2
		System.out.println("Insertamos cliente2");
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
		client2.setSubscription(subscription2);		
		client2.setOrder(order1);
		//clientService.add(client2);
		clientRepository.save(client2);
		
		
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
		//bookService.add(book1);
		bookRepository.save(book1);
		
		// Cremos book1
		Book book2 = new Book();
		book2.setName("libro2");
		book2.setAuthor("author2");
		book2.setIsbn("isbn2");
		book2.setCategory("category2");
		book2.setEditorial("editorial2");
		book2.setSynopsis("synopsis2");
		book2.setOrderBook(order2);
		//bookService.add(book2);
		bookRepository.save(book2);
		
		subscriptions = (List<Subscription>) subscriptionRepository.findAll();

		// Comprobamos que los datos se han guardado en la lista y en la BD
		assert !subscriptions.isEmpty();
		assert subscriptions.size() == subscriptionRepository.count();


		users = (List<User>) userRepository.findAll();
		assert !users.isEmpty();

		books = (List<Book>) bookRepository.findAll();
		assert !books.isEmpty();

		orders = (List<Order>) orderRepository.findAll();
		assert !orders.isEmpty();
		
	}
	
	
	/**
	 * Test en el que actualizaremos los registros
	 * Se comprueba si después de actualizar el valor cambia.
	 */
	@Test
	void test2() {
		System.out.println("*************************** TEST UPDATE ***************************");
		
		// ********** USER ********** //
		
		// Obtenemos el usuario "admin"
		User user = userRepository.findById(2L).get();
		assert user.getAddress() == null;
		
		System.out.println("Actualizamos la dirección de user.");
		user.setAddress("Dirección actualizada");
		userRepository.save(user);
		assert user.getAddress().equals("Dirección actualizada");
		
		// ********** EMPLOYEE ********** //
		// Miramos el valor actual del apellido del empleado
		Employee employee = (Employee) userRepository.findByUsername("pedro");
		assertEquals(employee.getSurname(),"ruiz");
		
		System.out.println("Actualizamos el surname de employee.");
		// Actualizamos el valor
		employee.setSurname("Ruiz Martínez");
		assertEquals(employee.getSurname(),"Ruiz Martínez");
		
		// ********** CLIENTE 1 ********** //		
		// Comprobamos que el cliente 1 tiene una subscripcion Familiar
		assertEquals(clientRepository.findById(3L).get().getSubscription().getType(), "Familiar");
		
		System.out.println("Actualizamos la subscripción del client1.");
		// Actualizamos el cliente1 con la nueva subscripcion		
		Client client1 = (Client) users.get(2);
		client1.setSubscription(subscriptionRepository.findById(2L).get());
		clientRepository.save(client1);
		// Comprobamos que realmente se ha actualizado.
		assertEquals(client1.getSubscription().getType(), clientRepository.findById(3L).get().getSubscription().getType());
		
		// ********** SUBSCRIPTION ********** //
		// Comprobamos que la subscripción 2 tiene un precio de 10
		assert subscriptionRepository.findById(2L).get().getPrice() == 10;
		
		System.out.println("Actualizamos el precio de la subscripcion sub.");
		// Capturamos la subscripcion de la lista y actualizamos el precio
		Subscription sub = subscriptions.get(1);
		sub.setPrice(20);
		subscriptionRepository.save(sub);
		// Comprobamos de nuevo la subscripción 2 a ver si ha cambiado el precio de 10 a 20
		assert subscriptionRepository.findById(2L).get().getPrice() != 10;
		assert subscriptionRepository.findById(2L).get().getPrice() == 20;
		
		
		// ********** ORDER ********** //
		// Comprobamos que la Order1 está activa
		assert orderRepository.findById(1L).get().isActive() == true;
		// Cambiamos a NO ACTIVA
		System.out.println("Actualizamos el estado de la order1 a false o no active.");
		Order order1 = orderRepository.findById(1L).get();
		order1.setActive(false);
		orderRepository.save(order1);
		// Comprobamos que el cambio ha tenido efecto.
		assert orderRepository.findById(1L).get().isActive() == false;
	}
	
	
	/**
	 *  Test para comprobar que el campo ID de los objetos Subscription y el email de User 
	 *  están configurados como "unique=true"
	 */
	@Test
	void test3 () {		

		// Comprobamos que no podemos introducir una subscripción con el mismo "id"		
		// Debe saltar una excepción del tipo "JpaSystemException"		 
		JpaSystemException thrown1 = Assertions.assertThrows(JpaSystemException.class, () -> {
			Subscription subscription3 = new Subscription();
			subscription3.setId_sub(1L);
			subscription3.setType("Básica");
			subscription3.setPrice(10);			
			subscriptionRepository.save(subscription3);
		}, "JpaSystemException was expected");		

		assertEquals("A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: "
				+ "com.bookaro.api.models.Subscription.allClients; nested exception is org.hibernate.HibernateException: "
				+ "A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: "
				+ "com.bookaro.api.models.Subscription.allClients", thrown1.getMessage());


		// Actualizaremos el email de cliente1 al mismo que tiene employee "pedro@bookaro.com"
		// Debería saltar una excepción puesto que el campo está calificado como "unique=true"
		Exception thrown2 = Assertions.assertThrows(Exception.class, () -> {
			User cliente1 = (Client)users.get(2);
			cliente1.setEmail("pedro@bookaro.com");
			userRepository.save(cliente1);
		}, "Exception was expected");

	}
	

	
	/**
	 * Test en el que borramos todos los registros
	 */
	@Test
	@Disabled
	void test4 () {
		orderRepository.deleteAll();
		subscriptionRepository.deleteAll();
		bookRepository.deleteAll();
		userRepository.deleteAll();
		
		assert orderRepository.count() == 0;
		assert subscriptionRepository.count() == 0;
		assert bookRepository.count() == 0;
		assert userRepository.count() == 0;
	}
	
	
	
	

	

}
