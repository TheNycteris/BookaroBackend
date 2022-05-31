package com.bookaro.api;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import com.bookaro.api.models.Author;
import com.bookaro.api.models.Book;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Editorial;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.Image;
import com.bookaro.api.models.Order;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.AuthorRepository;
import com.bookaro.api.repositories.BookRepository;
import com.bookaro.api.repositories.ClientRepository;
import com.bookaro.api.repositories.EditorialRepository;
import com.bookaro.api.repositories.EmployeeRepository;
import com.bookaro.api.repositories.ImageRepository;
import com.bookaro.api.repositories.OrderRepository;
import com.bookaro.api.repositories.SubscriptionRepository;
import com.bookaro.api.repositories.UserRepository;
import com.bookaro.api.utils.ImageUtility;
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
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BookaroTest {
	
	// Enlaces
	@Autowired 
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;	

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired 
	private EditorialRepository editorialRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	
	
	
	
	static List<User> users = new ArrayList<User>();
	static List<Client> clients = new ArrayList<Client>();
	static List<Employee> employees = new ArrayList<Employee>();
	static List<Order> orders = new ArrayList<Order>();
	static List<Book> books = new ArrayList<Book>();
	static List<Subscription> subscriptions = new ArrayList<Subscription>();
	static List<Image> images = new ArrayList<Image>();
	static List<Author> authors = new ArrayList<Author>();	
	static List<Editorial> editorials = new ArrayList<Editorial>();	
	
	static int cont = 1;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("*************************  COMIENZA LOS TESTS *************************");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("*************************  FINALIZADOS LOS TESTS *************************");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("*********** TEST " + cont + " INICIANDO ***********");	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("*********** TEST " + (cont-1) + " TERMINADO ***********");	
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
		
		System.out.println("Insertamos subscripción3");
		// subscription2
		Subscription subscription3 = new Subscription();		
		subscription3.setType("Sin Subscripción");
		subscription3.setPrice(0);
		subscriptionRepository.save(subscription3);
		
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
		employee.setActive(true);		
		employeeRepository.save(employee);
		

		// Creamos un usuario admin con los datos básicos
		System.out.println("Insertamos user admin");
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setName("Jill");
		admin.setSurname("Valentine");
		admin.setDni("0000010A");
		admin.setEmail("admin@bookaro.com");
		admin.setAge(25);
		admin.setRole("ROLE_ADMIN");	
		admin.setActive(true);
		userRepository.save(admin);
		
		// Creamos un usuario mod con los datos básicos
		System.out.println("Insertamos user user");
		User user = new User();
		user.setUsername("user");
		user.setPassword(passwordEncoder.encode("1234"));	
		user.setEmail("user@bookaro.com");						
		user.setRole("ROLE_USER");	
		user.setActive(true);
		userRepository.save(user);
		
		
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
		client1.setActive(true);
		client1.setSubscription(subscription1);		
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
		client2.setActive(true);				
		clientRepository.save(client2);		
			
		
		/**
		 * ************ Author ************		
		 */
		System.out.println("Insertamos author1");
		Author author1 = new Author();
		author1.setName("author1");
		author1.setNacionality("SPAIN");
		authorRepository.save(author1);
		
		System.out.println("Insertamos author2");
		Author author2 = new Author();
		author2.setName("author2");
		author2.setNacionality("GERMANY");
		authorRepository.save(author2);
		
		
		Author george = new Author();
		george.setName("GEORGE R.R. MARTIN");
		george.setNacionality("USA");
		authorRepository.save(george);
		
		
		/**
		 * ************ Editorial ************		
		 */
		System.out.println("Insertamos editorial1");
		Editorial editorial1 = new Editorial();
		editorial1.setName("editorial1");
		editorialRepository.save(editorial1);
		
		System.out.println("Insertamos editorial2");
		Editorial editorial2 = new Editorial();
		editorial2.setName("editorial2");
		editorialRepository.save(editorial2);
		
		Editorial gigamesh = new Editorial();
		gigamesh.setName("Gigamesh");
		editorialRepository.save(gigamesh);
		
		
		/**
		 * ************ Book ************		
		 */
		// Creamos book1
		/*System.out.println("Insertamos book1");
		Book book1 = new Book();
		book1.setName("libro1");		
		book1.setAuthor(author1);
		book1.setIsbn("isbn1");
		book1.setCategory("category1");
		book1.setEditorial(editorial1);
		book1.setSynopsis("synopsis1");	
		book1.setActive(true);
		bookRepository.save(book1);*/
		
		// Creamos book2
		/*System.out.println("Insertamos book2");
		Book book2 = new Book();
		book2.setName("libro2");		
		book2.setAuthor(author2);
		book2.setIsbn("isbn2");
		book2.setCategory("category2");
		book2.setEditorial(editorial2);
		book2.setSynopsis("synopsis2");	
		book2.setActive(true);
		bookRepository.save(book2);	*/	
		
		
		// Creamos book1
		System.out.println("Insertamos book1");
		Book book1 = new Book();
		book1.setName("Juego de Tronos (Saga Canción de Hielo y Fuego)");		
		book1.setAuthor(george);
		book1.setIsbn("9788496208490");
		book1.setCategory("Novela fantástica");
		book1.setEditorial(gigamesh);
		book1.setSynopsis("Canción de hielo y fuego: Libro primero. "
				+ "La novela río más espectacular jamás escrita. "
				+ "Tras el largo verano, el invierno se acerca a los Siete Reinos. ");	
		book1.setActive(true);
		bookRepository.save(book1);

		// Creamos book2
		System.out.println("Insertamos book2");
		Book book2 = new Book();
		book2.setName("Juego de Tronos (Choque de Reyes)");		
		book2.setAuthor(george);
		book2.setIsbn("9788496208506");
		book2.setCategory("Novela fantástica");
		book2.setEditorial(gigamesh);
		book2.setSynopsis("Canción de hielo y fuego: Libro segundo. "
				+ "La novela río más espectacular jamás escritaUn cometa"
				+ " del color de la sangre hiende el cielo, cargado de malos augurios.");	
		book2.setActive(true);
		bookRepository.save(book2);		

		// Creamos book3
		System.out.println("Insertamos book3");
		Book book3 = new Book();
		book3.setName("Juego de Tronos (Tormenta de Espadas)");		
		book3.setAuthor(george);
		book3.setIsbn("9788496208513");
		book3.setCategory("Novela fantástica");
		book3.setEditorial(gigamesh);
		book3.setSynopsis("Las huestes de los fugaces reyes de Poniente, "
				+ "descompuestas en hordas, asuelan y esquilman una tierra castigada "
				+ "por la guerra e indefensa ante un invierno que se anuncia inusitadamente crudo.");	
		book3.setActive(true);
		bookRepository.save(book3);

		// Creamos book4
		System.out.println("Insertamos book4");
		Book book4 = new Book();
		book4.setName("Juego de Tronos (Festín de cuervos)");		
		book4.setAuthor(george);
		book4.setIsbn("9788496208520");
		book4.setCategory("Novela fantástica");
		book4.setEditorial(gigamesh);
		book4.setSynopsis("Mientras los vientos del otoño desnudan los árboles, "
				+ "las últimas cosechas se pudren en los pocos campos que no han "
				+ "sido devastados por la guerra, y por los ríos teñidos de rojo "
				+ "bajan cadáveres de todos los blasones y estirpes.");	
		book4.setActive(true);
		bookRepository.save(book4);		
		
				
		
		/**
		 * ************ Image ************	
		 */		
		System.out.println("Insertamos una imagen");
		String ubi = System.getProperty("user.dir");		
		String url = ubi + "/src/main/resources/captura.png";
		File file = new File(url);
		Image image = new Image();		
		/*try {
			InputStream input = new FileInputStream(file);			

			MultipartFile multi = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));				
			image = imageRepository.save(Image.builder()
					.id(multi.getSize())
					.name(multi.getOriginalFilename())
					.type(multi.getContentType())
					.book(book2)
					.image(ImageUtility.compressImage(multi.getBytes()))
					.build());				

		} catch (IOException e ) {			
			e.printStackTrace();
		}*/
		
		
		System.out.println("Insertamos una imagen");				
		url = ubi + "/src/main/resources/CHOQUE_DE_REYES.jpg";
		file = new File(url);
		image = new Image();		
		try {
			InputStream input = new FileInputStream(file);			

			MultipartFile multi = new MockMultipartFile("file", file.getName(), "image/jpeg", IOUtils.toByteArray(input));				
			image = imageRepository.save(Image.builder()
					.id(multi.getSize())
					.name(multi.getOriginalFilename())
					.type(multi.getContentType())
					.book(book2)
					.image(ImageUtility.compressImage(multi.getBytes()))
					.build());				

		} catch (IOException e ) {			
			e.printStackTrace();
		}
		
		// Imagen 2
		url = ubi + "/src/main/resources/FESTIN_DE_CUERVOS.jpg";
		file = new File(url);
		image = new Image();		
		try {
			InputStream input = new FileInputStream(file);			

			MultipartFile multi = new MockMultipartFile("file", file.getName(), "image/jpeg", IOUtils.toByteArray(input));				
			image = imageRepository.save(Image.builder()
					.id(multi.getSize())
					.name(multi.getOriginalFilename())
					.type(multi.getContentType())
					.book(book4)
					.image(ImageUtility.compressImage(multi.getBytes()))
					.build());				

		} catch (IOException e ) {			
			e.printStackTrace();
		}
		
		
		// Imagen 3
		url = ubi + "/src/main/resources/SAGA_CANCION_DE_HIELO_Y_FUEGO 1.jpg";
		file = new File(url);
		image = new Image();		
		try {
			InputStream input = new FileInputStream(file);			

			MultipartFile multi = new MockMultipartFile("file", file.getName(), "image/jpeg", IOUtils.toByteArray(input));				
			image = imageRepository.save(Image.builder()
					.id(multi.getSize())
					.name(multi.getOriginalFilename())
					.type(multi.getContentType())
					.book(book1)
					.image(ImageUtility.compressImage(multi.getBytes()))
					.build());				

		} catch (IOException e ) {			
			e.printStackTrace();
		}
		
		
		// Imagen 4
		url = ubi + "/src/main/resources/TORMENTA_DE_ESPADAS.jpg";
		file = new File(url);
		image = new Image();		
		try {
			InputStream input = new FileInputStream(file);			

			MultipartFile multi = new MockMultipartFile("file", file.getName(), "image/jpeg", IOUtils.toByteArray(input));				
			image = imageRepository.save(Image.builder()
					.id(multi.getSize())
					.name(multi.getOriginalFilename())
					.type(multi.getContentType())
					.book(book3)
					.image(ImageUtility.compressImage(multi.getBytes()))
					.build());				

		} catch (IOException e ) {			
			e.printStackTrace();
		}
		
		/**
		 * ************ Order ************		
		 */
		System.out.println("Insertamos Order1");
		// Creamos una Order
		Order order1 = new Order();
		order1.setStartDate(new Date());
		order1.setActive(true);		
		order1.setClient(client1);		
		order1.setBook(book1);
		orderRepository.save(order1);
		
		System.out.println("Insertamos Order2");
		// Creamos una Order
		Order order2 = new Order();
		order2.setStartDate(new Date());
		order2.setActive(true);		
		order2.setClient(client2);
		order2.setBook(book2);
		orderRepository.save(order2);	

		System.out.println("Insertamos Order3");
		// Creamos una Order
		Order order3 = new Order();
		order3.setStartDate(new Date());
		order3.setActive(true);		
		order3.setClient(client1);
		order3.setBook(book2);
		orderRepository.save(order3);	
		
						
				
		// Comprobamos que los datos se han guardado en la lista y en la BD
		subscriptions = (List<Subscription>) subscriptionRepository.findAll();		
		assert !subscriptions.isEmpty();
		assert subscriptions.size() == subscriptionRepository.count();

		users = (List<User>) userRepository.findAll();
		assert !users.isEmpty();
		assert users.size() == userRepository.count();

		books = (List<Book>) bookRepository.findAll();
		assert !books.isEmpty();
		assert books.size() == bookRepository.count();

		orders = (List<Order>) orderRepository.findAll();
		assert !orders.isEmpty();	
		assert orders.size() == orderRepository.count();	
		
		images = (List<Image>) imageRepository.findAll();
		assert !images.isEmpty();
		assert images.size() == imageRepository.count();
		
		authors = authorRepository.findAll();
		assert !authors.isEmpty();
		assert authors.size() == authorRepository.count();
		
		editorials = editorialRepository.findAll();
		assert !editorials.isEmpty();
		assert editorials.size() == editorialRepository.count();
		
		cont++; // Actualizamos variable	
		
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
		assertEquals(clientRepository.findById(4L).get().getSubscription().getType(), "Familiar");
		
		System.out.println("Actualizamos la subscripción del client1.");
		// Actualizamos el cliente1 con la nueva subscripcion		
		Client client1 = (Client) users.get(3);
		client1.setSubscription(subscriptionRepository.findById(2L).get());
		clientRepository.save(client1);
		// Comprobamos que realmente se ha actualizado.
		assertEquals(client1.getSubscription().getType(), clientRepository.findById(4L).get().getSubscription().getType());
		
		
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
		//order1.setClient(clientRepository.findClientByUsername("cliente2"));
		orderRepository.save(order1);
		// Comprobamos que el cambio ha tenido efecto.
		assert orderRepository.findById(1L).get().isActive() == false;
		
		// ********** Book ********** //
		// Actualizamos el nombre del libro
		/*System.out.println("Actualizamos el nombre del libro.");
		Book book = bookRepository.findById(1L).get();
		book.setName("Libro actualizado");
		bookRepository.save(book);
		assert bookRepository.findById(book.getId()).get().getName().equals("Libro actualizado");*/
		
		// ********** Author ********** //
		// Actualizamos la nacionalidad del author
		System.out.println("Actualizamos la nacionalidad del Autor.");
		Author autor = authorRepository.findById(1L).get();
		autor.setNacionality("USA");
		authorRepository.save(autor);
		assert authorRepository.findById(1L).get().getNacionality().equals("USA");
		
		// ********** Editorial ********** //
		// Actualizamos el nombre del author
		System.out.println("Actualizamos el nombre de la editorial.");
		Editorial editorial = editorialRepository.findById(1L).get();
		editorial.setName("Editorial actualizada");
		editorialRepository.save(editorial);
		assert editorialRepository.findById(1L).get().getName().equals("Editorial actualizada");
		
		
		cont++; // Actualizamos variable
	}
	
	
	/**
	 *  Test para comprobar que el campo ID de los objetos Subscription y el email de User 
	 *  están configurados como "unique=true"
	 */
	@Test
	void test3 () {	
		System.out.println("*************************** TEST EXCEPTION ***************************");
		System.out.println("Comprobamos que no podemos introducir una subscripción con el mismo id.");
		// Comprobamos que no podemos introducir una subscripción con el mismo "id" y tipo de subscripción			
		Assertions.assertThrows(Exception.class, () -> {
			Subscription subscription4 = new Subscription();
			subscription4.setId_sub(1L);
			subscription4.setType("Básica");
			subscription4.setPrice(10);			
			subscriptionRepository.save(subscription4);
		}, "Exception was expected");		


		System.out.println("Actualizaremos el email de cliente1 al mismo que tiene employee pedro@bookaro.com");
		// Actualizaremos el email de cliente1 al mismo que tiene employee "pedro@bookaro.com"
		// Debería saltar una excepción puesto que el campo está calificado como "unique=true"
		Assertions.assertThrows(Exception.class, () -> {
			User cliente1 = (Client)users.get(4);
			cliente1.setEmail("pedro@bookaro.com");
			userRepository.save(cliente1);
		}, "Exception was expected");
		
		cont++; // Actualizamos variable

	}	

	
	/**
	 * Test en el que borramos todos los registros
	 */
	@Test
	@Disabled
	void test4 () {
		System.out.println("*************************** TEST DELETE ***************************");		
		
		System.out.println("Borrado Orders");
		orderRepository.deleteAll();		
		System.out.println("Borrado Books");
		bookRepository.deleteAll();
		System.out.println("Borrado Authors");
		authorRepository.deleteAll();
		System.out.println("Borrado Editorials");
		editorialRepository.deleteAll();
		System.out.println("Borrado Users");
		userRepository.deleteAll();
		System.out.println("Borrado Subscriptions");
		subscriptionRepository.deleteAll();
		System.out.println("Borrado Images");
		imageRepository.deleteAll();
		
		// Comprobamos que se han borrado los registros
		assert orderRepository.count() == 0;
		assert subscriptionRepository.count() == 0;
		assert bookRepository.count() == 0;
		assert userRepository.count() == 0;
		assert imageRepository.count() == 0;
		
		cont++; // Actualizamos variable
	}
	
	
}
