package com.bookaro.api.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Pedro<br>
 * Clase Book: 
 * <li> Gestiona los libros.</li>
 * <li> Tine vinculacion con la clase Order</li>
 *
 */
@Entity
@Table(name = "book")
public class Book {
	
	// ****** Atributos de clase ******
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;	
	
	@ManyToOne()
    @JoinColumn(name = "orderBook")
	@JsonIgnore
	private Order orderBook;	
	
	private String name, author, category, editorial, synopsis;
	
	@Column(unique = true, nullable = false)
	private String isbn; 
	
	
	@OneToOne
	@JoinColumn(name = "image_id")
	private Image image;
	
	/**
	 * Constructor vacio
	 */
	public Book() {}

	// ******  Getter/Setter ****** 
	
	/**
	 * Getter ID del libro
	 * @return Retor el id del libro
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter ID
	 * @param id Recibe un string con el id del libro
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter name
	 * @return Retorna el nombre del libro
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter name
	 * @param name Recibe un string con el nombre del libro
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter author
	 * @return Retorna un string con el autor
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Setter author
	 * @param author Recibe un string con el autor
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Getter isbn
	 * @return Retorna el isbn del libro
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Setter isbn
	 * @param isbn Recibe un string con el isbn del libro
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * Getter category
	 * @return Retorna un string con la categoria del libro.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter category
	 * @param category Recibe un string con la categoria del libro.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Getter editorial
	 * @return Retorn un string con la editorial del libro.
	 */
	public String getEditorial() {
		return editorial;
	}

	/**
	 * Setter editorial
	 * @param editorial Recibe un string con la editorial del libro.
	 */
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	/**
	 * Getter synopsis
	 * @return Retorna la synopsis del libro en un string.
	 */
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * Setter synopsis
	 * @param synopsis Recibe un string con la synopsis del libro
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * Getter orderBook
	 * @return Retorna un objeto de tipo Order.
	 */
	public Order getOrderBook() {
		return orderBook;
	}
	
	/**
	 * Setter orderBook
	 * @param orderBook Recibe un objeto de tipo Order.
	 */
	public void setOrderBook(Order orderBook) {
		this.orderBook = orderBook;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}	
	
	
	
}
