package com.bookaro.api.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookaro.api.models.Book;
import com.bookaro.api.services.BookService;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de controlador de la entidad Book<br>
 * Inyecta la dependencia BookService
 *
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	
	/**
	 * Metodo que busca un libro por su autor
	 * @param author Recibe un String con el autor del libro
	 * @return Retorna un objeto Book.
	 */
	@GetMapping("/author/{author}")
	public Book findBookByAuthor(@PathVariable("author") String author) {		
		return bookService.findBookByAuthor(author);
	}

	/**
	 * Metodo que busca un libro por su category
	 * @param category Recibe un String con la categoria del libro
	 * @return Retorna un objeto Book.
	 */
	@GetMapping("/category/{category}")
	public Book findBookByCategory(@PathVariable("category") String category) {
		return bookService.findBookByCategory(category);
	}

	/**
	 * Metodo que busca un libro por su editorial
	 * @param editorial Recibe un String con la editorial del libro
	 * @return Retorna un objeto Book.
	 */
	@GetMapping("/editorial/{editorial}")
	public Book findBookByEditorial(@PathVariable("editorial") String editorial) {
		return bookService.findBookByEditorial(editorial);
	}


	/**
	 * @author Pedro<br>
	 * Metodo que busca un libro por su nombre
	 * @param name Recibe un String con el nombre del libro
	 * @return Retorna un objeto Book.
	 */
	@GetMapping("/name/{name}")
	public Book findBookByName(@PathVariable("name") String name) {
		return bookService.findBookByName(name);
	}
	
	
	/**
	 * @author Pedro<br>
	 * Metodo que devulve una lista de libros	  
	 * @return Retorna una lista con todos los libros en la BD.
	 */
	@GetMapping("/all")
	public ArrayList<Book> getAllBooks(){
		return bookService.findAll();
	}
	
	
	/**
	 * @author Pedro<br>
	 * Metodo que devuelve un libro por su id
	 * @param id Recibe un Long con el id del libro
	 * @return Retorna un objeto Book
	 */
	@GetMapping (value = "{id}")
	public Optional<Book> getBookId (@PathVariable ("id")long id) {
		return bookService.findById(id);
	}
	
	
	/**
	 * @author Pedro<br>
	 * Metodo para crear o añadir un libro a la BD
	 * @param book Recibe un objeto Book
	 * @return Retorna un String con el resultado.
	 */
	@PostMapping("/insert")
	public String addBook (@RequestBody Book book) {
		if (book != null) {
			bookService.add(book);
			return "Added a book";
		} else {
			return "Request does not contain a body";
		}
	}
	
	
	/**
	 * @author Pedro<br>
	 * Metodo para actualizar un libro
	 * @param book Recibe un objeto Book
	 * @return Retorna un String con el resultado.
	 */
	@PutMapping("/update")
	public String updateBook(@RequestBody Book book) {
	    if(book != null) {
	    	bookService.update(book);
	        return "Updated book.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	/**
	 * @author Pedro<br>
	 * Metodo para eliminar un libro de la BD
	 * @param id Recibe un long con el id del libro
	 * @return Retorna un String con el resultado.
	 */
	@DeleteMapping("{id}")
	public String deleteBook (@PathVariable("id") long id) {
		if(id > 0) {
			if(bookService.delete(id)) {
				return "Deleted the book.";
			} else {
				return "Cannot delete the book.";
			}
		}
		return "The id is invalid for the book.";
	}

}
