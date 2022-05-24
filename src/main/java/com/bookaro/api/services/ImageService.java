package com.bookaro.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.bookaro.api.models.Book;
import com.bookaro.api.models.Image;
import com.bookaro.api.repositories.ImageRepository;
import com.bookaro.api.utils.ImageUploadResponse;
import com.bookaro.api.utils.ImageUtility;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


/** 
 * @author Pedro<br>
 * Clase que hace la funcion de Service de la entidad Image
 * Inyecto la dependencia ImageRepository
 */
@Service
public class ImageService {

	@Autowired
	ImageRepository imageRepository;
	
	
	/**
	 * Metodo que busca una imagen por su libro
	 * @param book Recibe objeto Book
	 * @return Retorna objeto Image
	 */
	public Optional<Image> findByBook(Book book) {
		return imageRepository.findByBook(book);
	}


	/**
	 * Metodo para subir una imagen a la BD del servidor
	 * @param file Recibe un parametro de tipo MultipartFile
	 * @param id Recibe un objeto Book que representa el id del libro
	 * @return Retorna un objeto ImageUploadResponse
	 * @throws IOException Puede lanzar IOException
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public ImageUploadResponse upload(@RequestParam("image") MultipartFile file, Book id) throws IOException {
		imageRepository.save(Image.builder()
				.id(file.getSize())
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.book(id)
				.image(ImageUtility.compressImage(file.getBytes())).build());
		ImageUploadResponse image = new ImageUploadResponse(file.getOriginalFilename());
		return image;
	}	

	
	/**
	 * Metodo para actualizar una imagen
	 * @param file recibe un parametro de tipo MultipartFile
	 * @param id recibe un Long con el id de la imagen
	 * @param id_book recibe un objeto Book que representa el ID del libro.
	 * @return Retorna objeto Image
	 * @throws IOException Puede lanzar IOException
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public Image updateImage (@RequestParam("image") MultipartFile file, Long id, Book id_book) throws IOException {				
		Image imagen = imageRepository.save(Image.builder()				
				.id(id)
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.book(id_book)
				.image(ImageUtility.compressImage(file.getBytes())).build());
		
		return imagen;
	}
	

	/**
	 * Metodo para obtener la informacion de la imagen
	 * @param name Recibo un parametro de tipo String con el nombre de la imagen
	 * @return Retorna un Objeto Image
	 * @throws IOException Puede lanzar una excepción de tipo IOException
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public Image getImageDetails(@PathVariable("name") String name) throws IOException {
		final Optional<Image> dbImage = imageRepository.findByName(name);
		
		return Image.builder()
				.id(dbImage.get().getId())
				.name(dbImage.get().getName())
				.type(dbImage.get().getType())
				.book(dbImage.get().getBook())
				.image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
	}
		
	
	
	/**
	 * Metodo para buscar imagenes por ID
	 * @param id Recibe un Long con el id de la imagen
	 * @return Retorna un objeto Image
	 */	
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Image findById(@PathVariable("id") Long id) {
		Optional<Image> img = imageRepository.findById(id);
		
		return Image.builder()
				.id(img.get().getId())
				.name(img.get().getName())
				.type(img.get().getType())
				.book(img.get().getBook())
				.image(ImageUtility.decompressImage(img.get().getImage())).build();		
	}
	
	
	/**
	 * Metodo que obtine una lista de Image
	 * @return Retorna la lista.
	 */	
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Image> findAll() {
		List<Image> images = (List<Image>) imageRepository.findAll();

		for (Image img: images) {
			Image.builder()
			.id(img.getId())
			.name(img.getName())
			.type(img.getType())
			.book(img.getBook())
			.image(ImageUtility.decompressImage(img.getImage())).build();			
		}

		return images;
	}


	/**
	 * Metodo que muestra una imagen
	 * @param name Recibe un String con el nombre de la imagen
	 * @return Retorna un array de bytes
	 * @throws IOException Puede lanzar IOException
	 */	
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
		final Optional<Image> dbImage = imageRepository.findByName(name);
		return ResponseEntity
				.ok()
				.contentType(MediaType.valueOf(dbImage.get().getType()))
				.body(ImageUtility.decompressImage(dbImage.get().getImage()));
	}

	
	/**
	 * Metodo que borra una imagen por su id
	 * @param id Recibe long con el id
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public void deleteById(Long id) {
		imageRepository.deleteById(id);
	}
	

}
