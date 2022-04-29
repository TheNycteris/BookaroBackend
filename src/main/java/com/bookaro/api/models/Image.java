package com.bookaro.api.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Builder;
import lombok.Data;


/**
 * 
 * @author Pedro <br>
 * Clase que hace la función de entidad o tabla para almacenar imagenes o portadas de libros
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "image")
@Data
@Builder
public class Image {
	
	// Atriburos de clase
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "image", unique = false, nullable = false, length = 100000)
	private byte[] image;
	
	
	// Enlace con la clase Book
	/*@OneToOne(mappedBy="image")
	//@JsonIgnore
	private Book book;*/
	
	@OneToOne
	@JoinColumn(name = "book_id")
	//@JsonIgnore <--------------------
	private Book book;
	
	/**
	 * Constructor
	 * @param id Recibe un Long con el id de la imagen
	 * @param name Recibe un String con el name de la imagen
	 * @param type Recibe un String con el type de la imagen
	 * @param image Recibe un array de bytes de la imagen
	 * @param book Recibe un objeto de tipo Book
	 */
	public Image(Long id, String name, String type, byte[] image, Book book) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.image = image;
		this.book = book;
	}
	
	
	/**
	 * Constructor vacio
	 */
	public Image() {}
	
	// Getters/Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public Book getBook () {
		return book;
	}
	
	public void setBook (Book book) {
		this.book = book;
	}

}
