package com.bookaro.api.models;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "image", unique = false, nullable = false, length = 100000)
	private byte[] image;	
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE) 
	@JoinColumn(name = "book_id")	
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
	/**
	 * Getter atributo id
	 * @return Retorna Long con el id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter id
	 * @param id Recibe un objeto Long con el id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter atributo name
	 * @return Retorna String con el name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter name
	 * @param name Recibe String con el name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter atributo type
	 * @return Retorna String con el type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter atributo type
	 * @param type Recibe String con el type.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
	 * Getter atributo image
	 * @return Retorn array de byte[]
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Setter atributo image
	 * @param image Recibe un array de byte[]
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	/**
	 * Getter atributo book
	 * @return Retorn objeto Book.
	 */
	public Book getBook () {
		return book;
	}
	
	/**
	 * Setter atributo book
	 * @param book Recibe objeto Book.
	 */
	public void setBook (Book book) {
		this.book = book;
	}

}
