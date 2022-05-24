package com.bookaro.api.models;

import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)	
	private List<Order> orders;	
	
	private String name, category, synopsis;
	
	@Column(unique = true, nullable = false)
	private String isbn;	
	
	private boolean active;	
	
	@OneToOne(mappedBy="book")
	@OnDelete(action = OnDeleteAction.CASCADE)	
	private Image image;	
	
	@ManyToOne()
    @JoinColumn(name = "id_author")	
	private Author author;
	
	@ManyToOne()
    @JoinColumn(name = "id_editorial")	
	private Editorial editorial;
	
	
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
	@JsonProperty("orders")
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Setter atributo List<Order>
	 * @param orders Recibe una lista de objetos Order
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
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
	@JsonProperty(access = Access.WRITE_ONLY)
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


	/**
	 * Getter author
	 * @return Retorna objeto Author
	 */
	public Author getAuthor() {
		return author;
	}


	/**
	 * Setter Author
	 * @param author Recibe objeto Author
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}


	/**
	 * Getter Editorial
	 * @return Retorna objeto Editorial
	 */
	public Editorial getEditorial() {
		return editorial;
	}


	/**
	 * Setter Editorial
	 * @param editorial Recibe objeto Editorial
	 */
	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}		
	
}
