package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import com.bookaro.api.models.Book;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.BookRepository;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Service para la entidad Book<br>
 * Inyecto la dependencia BookRepository.
 *
 */
@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	/** 
	 * Metodo que devuelve un libro por su nombre	
	 * @param name Recibe un String con el nombre del libro
	 * @return Retorna un objeto Book.
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Book findByName(String name) {
		return bookRepository.findByName(name);
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
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public Book add (Book book) {
		return bookRepository.save(book);
	}
	
	
	/**
	 * Metodo para actualizar un libro
	 * @param book Recibo un objeto Book
	 * @return Retorna true o false dependiendo de si puede o no actualizarlo.
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean update(Book book) {
	    try {
	    	bookRepository.save(book);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * Metodo para eliminar un libro de la BD
	 * @param id Recibe un long con el id del libro
	 * @return Retorna true o false dependiendo del resultado.
	 */
	@PostAuthorize(value = "hasRole('ADMIN')")
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
