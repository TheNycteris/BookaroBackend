package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Book;
import com.bookaro.api.repositories.BookRepository;

/** 
 * @author Pedro<br>
 * Clase que hace la funcion de Service para la entidad Book<br>
 * Inyecta la dependencia BookRepository.
 */
@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	
	
	/**
	 * Metodo que filtra una lista de libros por su estado
	 * @param active Recibe boolean 
	 * @return Retorna una lista de lisbros
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Book> findBookByActive(boolean active) {
		return bookRepository.findBookByActive(active);
	}

	/**
	 * Metodo para buscar un libro por su autor
	 * @param author Recibe un String con el autor
	 * @return Retorna un Book
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Book> findBookByAuthor(String author) {
		List<Book> books = bookRepository.findBookByAuthor(author);		
		return books;		
	}

	/**
	 * Metodo para buscar un libro por su categoria
	 * @param category Recibe un String con la categoria
	 * @return Retorna un Book
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Book> findBookByCategory(String category) {
		return bookRepository.findBookByCategory(category);
	}

	/**
	 * Metodo para buscar un libro por su editorial
	 * @param editorial Recibe un String con la editorial
	 * @return Retorna un Book
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Book> findBookByEditorial(String editorial) {
		return bookRepository.findBookByEditorial(editorial);
	}

	/** 
	 * Metodo que devuelve un libro por su nombre	
	 * @param name Recibe un String con el nombre del libro
	 * @return Retorna un objeto Book.
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Book findBookByName(String name) {
		return bookRepository.findBookByName(name);
	}

	/**
	 * Metodo que devuelve una lista de libros
	 * @return Retorna una lista con todos los libros de la BD.
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public ArrayList<Book> findAll(){
		return (ArrayList<Book>) bookRepository.findAll();
	}
	
	/**
	 * Metodo que devuelve un libro por su id
	 * @param id Recibe un Long con el id del libro.
	 * @return Retorna un objeto Book
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Optional<Book> findById (Long id) {
		return bookRepository.findById(id);
	}
	
	
	/**
	 * Metodo para insertar o crear un libro
	 * @param book Recibe un objeto Book.
	 * @return Retorn un objeto Book.
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public Book add (Book book) {
		return bookRepository.save(book);
	}
	
	
	/**
	 * Metodo para actualizar un libro
	 * @param book Recibo un objeto Book
	 * @return Retorna true o false dependiendo de si puede o no actualizarlo.
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean update(Book book) {
	    try {
	    	bookRepository.save(book);
	        return true;
	    } catch (Exception e) {	        
	        return false;
	    }
	}
	
	/**
	 * Metodo para eliminar un libro de la BD
	 * @param id Recibe un long con el id del libro
	 * @return Retorna true o false dependiendo del resultado.
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean delete (long id) {
		try {
			bookRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

}
