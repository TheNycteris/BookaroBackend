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

	
	@GetMapping("/all")
	public List<Author> findAll() {
		return authorService.findAll();
	}

	@GetMapping("/id/{id}")
	public Optional<Author> findById(@PathVariable("id") Long id) {
		return authorService.findById(id);
	}

	@PostMapping("/insert")
	public String add(@RequestBody Author author) {
		if (authorService.add(author)) {
			return "Added Author";
		} else {
			return "Cannot added Auhor";
		}
	}

	
	@PutMapping("/update")
	public String update(@RequestBody Author author) {
		if (authorService.update(author)) {
			return "Update Author";
		} else {
			return "Error actualizando";
		}
		
	}

	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		if (authorService.delete(id)) {
			return "Delete Author";
		} else {
			return "Cannot delete Author";
		}
	}
	
	

}
