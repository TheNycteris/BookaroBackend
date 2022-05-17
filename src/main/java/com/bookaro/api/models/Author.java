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
	
	private String name, nacionality;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
	private List<Book> books;
	
	public Author() {}

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

	public String getNacionality() {
		return nacionality;
	}

	public void setNacionality(String nacionality) {
		this.nacionality = nacionality;
	}

	public List<Book> getBooks() {
		return books;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	

}
