/**
 * 
 */
package com.bookaro.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
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

/**
 * @author Pedro
 *
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)

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

		System.out.println("Insertamos subscripción1");
		// subscription2
		Subscription subscription2 = new Subscription();		
		subscription2.setType("Básica");
		subscription2.setPrice(10);
		subscriptionRepository.save(subscription2);

		
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
		//employeeService.add(employee);
		employeeRepository.save(employee);
		

		// Creamos un usuario admin con los datos básicos
		System.out.println("Insertamos user admin");
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));	
		admin.setEmail("admin@bookaro.com");						
		admin.setRole("ROLE_ADMIN");
		//userService.create(admin);
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
		//clientService.add(client1);	
		clientRepository.save(client1);		
		
		
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
	

	/*@Test
	void test1() {
		System.out.println("test10");
		subscriptionRepository.deleteAll();		
		
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
		
		//List<Subscription> subscriptions = (List<Subscription>) subscriptionRepository.findAll();
		subscriptions = (List<Subscription>) subscriptionRepository.findAll();
		
		// Comprobamos que los datos se han guardado en la lista y en la BD
		assert !subscriptions.isEmpty();
		assert subscriptions.size() == subscriptionRepository.count();
		
		// Hacemos un update
	}	
	
	
	@Test
	void test2() {
		System.out.println("test20");
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
		//client1.setSubscription(subscriptions.get(0));
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
		
		users = (List<User>) userRepository.findAll();
		
		assert !users.isEmpty();
	}
	
	@Test
	void test3() {	
		System.out.println("*************************** TEST INSERT ORDER ***************************");
		
		orderRepository.deleteAll();
		
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
		
		orders = (List<Order>) orderRepository.findAll();
		
		assert !orders.isEmpty();

	}
	
	@Test
	void test4 () {
		
		System.out.println("*************************** TEST INSERT BOOK ***************************");
		
		bookRepository.deleteAll();

		// Cremos book1
		Book book1 = new Book();
		book1.setName("libro1");
		book1.setAuthor("author1");
		book1.setIsbn("isbn1");
		book1.setCategory("category1");
		book1.setEditorial("editorial1");
		book1.setSynopsis("synopsis1");
		//book1.setOrderBook(order1);
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
		//book2.setOrderBook(order2);
		//bookService.add(book2);
		bookRepository.save(book2);
		
		books = (List<Book>) bookRepository.findAll();
		
		assert !books.isEmpty();
	}
	
	@Test
	void test5 () {
		System.out.println("*************************** TEST UPDATE USERS ***************************");
		User user = userRepository.findById(1L).get();
		user.setAddress("Dirección actualizada");
		userRepository.save(user);
		
		assert userRepository.findById(1L).get().getAddress().equals("Dirección actualizada");
	}*/
	
	
	
	

}
