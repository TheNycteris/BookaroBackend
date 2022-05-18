package com.bookaro.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bookaro.api.models.Editorial;
import com.bookaro.api.repositories.EditorialRepository;

@Service
public class EditorialService {
	
	@Autowired
	private EditorialRepository editorialRepository;
	
	
	
	/**
	 * Metodo que devuelve todos los objetos Editorial
	 * @return Retorna una lista de objetos Editorial
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Editorial> findAll() {
		return editorialRepository.findAll();
	}

	/**
	 * Metodo que busca una Editorial por su id
	 * @param id Recibe Long con el id
	 * @return Retorna objeto Editorial
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Optional<Editorial> findById(Long id) {
		return editorialRepository.findById(id);
	}
	
	/**
	 * Metodo para añadir una editorial.
	 * @param editorial Recibe objeto Editorial
	 * @return Retorna true o false
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean add (Editorial editorial) {
		try {
			editorialRepository.save(editorial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Metodo para actualizar una editorial
	 * @param editorial Recibe objeto Editorial
	 * @return Retorna true o false
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean update (Editorial editorial) {
		try {
			editorialRepository.save(editorial);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que elimina una Editorial por su id
	 * @param id Recibe Long con el id
	 * @return Retorna true o false
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean deleteById(Long id) {
		try {
			editorialRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}	
	

}
