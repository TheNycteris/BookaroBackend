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

import com.bookaro.api.models.Editorial;
import com.bookaro.api.services.EditorialService;

@RestController
@RequestMapping("/api/editorial")
public class EditorialController {
	
	@Autowired
	private EditorialService editorialService;

	
	/**
	 * Metod que devuelve todas las editoriales
	 * @return Retorna una lista de objetos Editorial
	 */
	@GetMapping("/all")
	public List<Editorial> findAllEditorial() {
		return editorialService.findAll();
	}

	/**
	 * Metodo que busca una Editorial por su id
	 * @param id Recibe Long con el id
	 * @return Retorna objeto Editorial
	 */
	@GetMapping("/id/{id}")
	public Optional<Editorial> findEditorialById(@PathVariable("id") Long id) {
		return editorialService.findById(id);
	}

	/**
	 * Metodo para eliminar añadir una editorial
	 * @param editorial Recibe objeto Editorial
	 * @return Retorna String con el mensaje
	 */
	@PostMapping("/insert")
	public String addEditorial(@RequestBody Editorial editorial) {
		if (editorialService.add(editorial)) {
			return "Added Editorial";
		} else {
			return "Cannot added Editorial";
		}		
	}

	/**
	 * Metodo para actualizar una editorial
	 * @param editorial Recibe objeto Editorial
	 * @return Retorna String con el mensaje
	 */
	@PutMapping("/update")
	public String updateEditorial(@RequestBody Editorial editorial) {
		if (editorialService.update(editorial)) {
			return "Update Editorial"; 
		} else {
			return "Cannot update editorial";
		}		
	}

	/**
	 * Metodo para eliminar una editorial
	 * @param id Recibe Long con el id
	 * @return Retorna String con el mensage
	 */
	@DeleteMapping("/delete/{id}")
	public String deleteEditorialById(@PathVariable("id") Long id) {
		if (editorialService.deleteById(id)) {
			return "Delete Editorial";
		} else {
			return "Cannot delete Editorial";
		}
	}	

}
