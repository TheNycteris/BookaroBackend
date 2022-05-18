package com.bookaro.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Author;
import com.bookaro.api.repositories.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	/**
	 * Metodo que busca todos los autores de la BD
	 * @return Retorna lista de objetos Author
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	/**
	 * Metodo que busca un Author por su id
	 * @param id Recibe Long con el id.
	 * @return Retorna objeto Author
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Optional<Author> findById(Long id) {
		return authorRepository.findById(id);
	}
	
	/**
	 * Metodo para insertar un autor
	 * @param author Recibe objeto Author
	 * @return Retrona true o false
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean add (Author author) {
		try {
			authorRepository.save(author);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/**
	 * Metodo para actualizar un autor
	 * @param author Recibe objeto Author
	 * @return Retorna true o false
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean update(Author author) {
	    try {
	    	authorRepository.save(author);
	        return true;
	    } catch (Exception e) {	        
	        return false;
	    }
	}	

	/**
	 * Metodo para borrar un Author
	 * @param id Recibe Long con el id del author
	 * @return Retorna true o false
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean delete(Long id) {
		try {
			authorRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	

}
