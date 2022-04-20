package com.bookaro.api.models;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Pedro<br>
 * Clase Order:
 * <li> Gestiona los alquileres de libros.</li>
 * <li> Tiene vinculación con las clases Client y Book</li>
 *
 */
@Entity
@Table(name = "orders")
public class Order {
	
	// ****** Atributos de clase ****** 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	private Date startDate;
	private boolean active;	
	
	@ManyToOne()
	@JsonIgnore
    @JoinColumn(name = "id_user")
	private Client client;
	
	@OneToMany(mappedBy = "orderBook", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Book> books; 

	/**
	 * Métodeo para contar order activas
	 * <b> Aún por implantar </b>
	 * @param clientId Recibe el id del cliente
	 * @return Retorna true o false dependien de la cantidad de libros adquiridos.
	 */
	public boolean countActiveOrders (long clientId) {	
		return false; // aún por implementar
	}
	
	
	// ******  Getter/Setter ******	
	
	/**
	 * Getter startDate
	 * @return Retorna un Date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setterr startDate
	 * @param startDate Recibe un parametro de tipo Date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter active
	 * @return Retorna true o false en funcion de si esta activa o no.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Setter active
	 * @param active Recibe un boolean para activar o desactivar la order
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Getter books
	 * @return Retorna una lista de libros asociados a la order
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Setter books
	 * @param books Recibe una lista de libros
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	/**
	 * Getter ID
	 * @return Retorna un Long con el ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter ID
	 * @param id Recibe el id como un Long
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * Getter clients
	 * @return Retorna un Objeto Client
	 */
	public Client getClient() {
		return client;
	}


	/**
	 * Setter clients
	 * @param clients Recibe un objeto Client
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
		
}
