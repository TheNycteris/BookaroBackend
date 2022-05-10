package com.bookaro.api.models;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Pedro<br>
 * Clase Book: 
 * <li> Gestiona los libros.</li>
 * <li> Tine vinculacion con la clase Order</li>
 *
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "book")
public class Book {
	
	// ****** Atributos de clase ******
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;	
	
	
	@OneToMany(mappedBy = "book", /*cascade = CascadeType.ALL*/ cascade = CascadeType.REMOVE, fetch=FetchType.EAGER/*, orphanRemoval = true*/ )
	@JsonIgnore 
	private List<Order> orders;
	
	
	private String name, author, category, editorial, synopsis;
	
	@Column(unique = true, nullable = false)
	private String isbn;	
	
	private boolean active;
	
	// Enlace con la clase Book
	@OneToOne(mappedBy="book"/*, orphanRemoval = true*/ )
	@OnDelete(action = OnDeleteAction.CASCADE) ////// <--
	@JsonIgnore
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
	 * Getter atributo List<Order>
	 * @return Retorna lista de objetos Order
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Setter atributo List<Order>
	 * @param orders Recibe una lista de objetos Order
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	/**
	 * Getter atributo image
	 * @return Retorn objeto Image
	 */
	public Image getImage() {
		return image;
	}
	

	/**
	 * Setter atributo image
	 * @param image Recibe un objeto Image
	 */
	public void setImage(Image image) {
		this.image = image;
	}


	/**
	 * Getter atributo active
	 * @return Retorna boolean
	 */
	public boolean isActive() {
		return active;
	}


	/**
	 * Setter atributo active
	 * @param active Recibe boolean
	 */
	public void setActive(boolean active) {
		this.active = active;
	}		
	
	
	
	
}
