package com.bookaro.api.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bookaro.api.models.Book;
import com.bookaro.api.models.Image;
import com.bookaro.api.services.BookService;
import com.bookaro.api.services.ImageService;
import com.bookaro.api.utils.ImageUploadResponse;


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
	 * @param id Recibe un objeto de tipo Book que representa el id del libro.
	 * @return Retorna un objeto ImageUploadResponse
	 * @throws IOException Puede lanzar IOException
	 */
	@PostMapping("/upload/image")
	public ImageUploadResponse upload(@RequestParam("image") MultipartFile file, @RequestParam("book_id") Book id) throws IOException {		
		return imageService.upload(file, id);		
	}
	
	
	

	/**
	 * Metodo para obtener la informacion de la imagen
	 * @param name Recibo un parametro de tipo String con el nombre de la imagen
	 * @return Retorna un Objeto Image
	 * @throws IOException Puede lanzar IOException.
	 */	
	@GetMapping(path = {"/get/image/info/{name}"})
	public Image getImageDetails(@PathVariable("name") String name) throws IOException {
		return imageService.getImageDetails(name);
	}
	
	
	
	/**
	 * Metodo que actualiza una imagen
	 * @param file Recibe objeto MultipartFile
	 * @param id Recibe Long con el id de la imagen
	 * @param book_id Recibe un objeto Book que representa el id del libro.
	 * @return Retorna un objeto Image
	 * @throws IOException Puede lanzar IOException
	 */
	@PutMapping("/update/image/{id}")	
	public Image updateImage (@RequestParam("image") MultipartFile file, 
			@PathVariable("id") Long id, @RequestParam("book_id") Book book_id) throws IOException {
		return imageService.updateImage(file, id, book_id);
	}



	/**
	 * Metodo para obtener lista de imagenes
	 * @return Retorna lista imagenes
	 */
	@GetMapping("/get/image/all")
	public List<Image> getAllImages() {
		return imageService.findAll();
	}



	/**
	 * Metodo que busca imagen por su ID.
	 * @param id Recibe un long con el ID de la imagen
	 * @return Retorna un objeto Image
	 */
	@GetMapping(path = {"/get/image/id/{id}"})
	public Image findImageById(@PathVariable("id") Long id) {
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


	/**
	 * Metodo que borra una imagen por su id
	 * @param id Recibe long con el id
	 */
	@DeleteMapping("/delete/image/{id}")
	public void deleteImageById(@PathVariable("id") Long id) {
		imageService.deleteById(id);
	}
	
	

}
