package com.bookaro.api.controllers;

import java.util.List;
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
import com.bookaro.api.models.Author;
import com.bookaro.api.services.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;

	/**
	 * Metodo que devuelve todos los autores
	 * @return Retorna una lista de objetos Author
	 */
	@GetMapping("/all")
	public List<Author> findAllAuthor() {
		return authorService.findAll();
	}

	/**
	 * Metodo que busca un Author por su id
	 * @param id Recibe Long con el id
	 * @return Retorna objeto Editorial
	 */
	@GetMapping("/id/{id}")
	public Optional<Author> findAuthorById(@PathVariable("id") Long id) {
		return authorService.findById(id);
	}

	/**
	 * Metodo para insertar una Author
	 * @param author Recibe objeto Author
	 * @return Retorna String con el mensage
	 */
	@PostMapping("/insert")
	public String addAuthor(@RequestBody Author author) {
		if (authorService.add(author)) {
			return "Added Author";
		} else {
			return "Cannot added Auhor";
		}
	}

	/***
	 * Metodo para actualizar un Author
	 * @param author Recibe un objeto Author
	 * @return Retorna String con el mensaje
	 */
	@PutMapping("/update")
	public String updateAuthor(@RequestBody Author author) {
		if (authorService.update(author)) {
			return "Update Author";
		} else {
			return "Error actualizando";
		}
		
	}

	/**
	 * Metodo para eliminar un autor
	 * @param id Recibe Long con el id
	 * @return Retorna String con el mensaje.
	 */
	@DeleteMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable("id") Long id) {
		if (authorService.delete(id)) {
			return "Delete Author";
		} else {
			return "Cannot delete Author";
		}
	}
	
	

}
