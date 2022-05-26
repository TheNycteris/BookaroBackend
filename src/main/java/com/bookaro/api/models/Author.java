package com.bookaro.api.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "author")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;	
	
	@Column(unique = true, nullable = false)
	private String name; 
	
	private String nacionality;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
	private List<Book> books;
	
	public Author() {}

	
	// ********* Getters/Setters ********* //	
	/**
	 * Getter id
	 * @return Retorna Long
	 */
	public Long getId() {
		return id;
	}	
	
	/**
	 * Setter id
	 * @param id Recibe Long
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * setter name
	 * @return Retorna String
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter name
	 * @param name Recibe String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter nacionality
	 * @return Retorna String
	 */
	public String getNacionality() {
		return nacionality;
	}

	/**
	 * setter nacionality
	 * @param nacionality Recibe String
	 */
	public void setNacionality(String nacionality) {
		this.nacionality = nacionality;
	}

	/**
	 * getter books
	 * @return Retorna lista de objetos Book
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * setter books
	 * @param books Recibe lista objetos book
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	public void setBooks(List<Book> books) {
		this.books = books;
	}	
	

}
