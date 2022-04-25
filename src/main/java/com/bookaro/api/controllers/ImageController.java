package com.bookaro.api.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookaro.api.models.Image;
import com.bookaro.api.repositories.ImageRepository;
import com.bookaro.api.services.ImageService;
import com.bookaro.api.utils.ImageUploadResponse;
import com.bookaro.api.utils.ImageUtility;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de controller de la entidad Image
 * Implementa o inyecta la dependencia ImageService
 *
 */
@RestController
@RequestMapping("/api/book")
public class ImageController {

	@Autowired
	ImageService imageService;


	/**
	 * Metodo para subir una imagen a la BD
	 * @param file Recibe un parametro de tipo MultipartFile
	 * @return Retorna un objeto ImageUploadResponse
	 * @throws IOException Puede lanzar IOException
	 */
	@PostMapping("/upload/imagen")
	public ImageUploadResponse upload(@RequestParam("image") MultipartFile file) throws IOException {		
		return imageService.upload(file);		
	}
	
	

	/**
	 * Metodo para obtener la informacion de la imagen
	 * @param name Recibo un parametro de tipo String con el nombre de la imagen
	 * @return Retorna un Objeto Image
	 * @throws IOException
	 */
	@GetMapping(path = {"/get/image/info/{name}"})
	public Image getImageDetails(@PathVariable("name") String name) throws IOException {
		return imageService.getImageDetails(name);
	}
	
	
	@GetMapping(path = {"/get/image/id/{id}"})
	public Image findById(@PathVariable("id") Long id) {
		return imageService.findById(id);
	}
	
	/**
	 * Metodo que muestra una imagen
	 * @param name Recibe un String con el nombre de la imagen
	 * @return Retorna un array de bytes
	 * @throws IOException Puede lanzar IOException
	 */
	@GetMapping(path = {"/get/image/{name}"})
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
		return imageService.getImage(name);
	}

}