package com.bookaro.api.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Pedro<br>
 * Clase Order:
 * <li> Gestiona los alquileres de libros.</li>
 * <li> Tiene vinculación con las clases Client y Book</li>
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
	@JoinColumn(name = "id_client")
	private Client client;
	
	
	@ManyToOne()
    @JoinColumn(name = "id_book")	
	private Book book;	

	/**
	 * Métodeo para contar order activas
	 * <b> Aún por implantar </b>
	 * @param client Recibe el id del cliente
	 * @return Retorna true o false dependien de la cantidad de libros adquiridos.
	 */
	public boolean countActiveOrders (Client client) {
		System.out.println(client.getId());
		System.out.println(this.getClient().getUsername());
		if (client.getSubscription().getType().equalsIgnoreCase("Sin Subscripción")) {
			System.out.println("Estoy en el primer IF");
			return false;
		} 
		int contador = 0;
		for (Order order: client.getOrders()) {
			if (order.isActive()) {
				contador++;
			}
		}
		
		System.out.println(contador);
		
		if ((contador > 1) && client.getSubscription().getType().equals("Básica")) {
			System.out.println("Estoy en el SEGUNDO IF");
			return false;
		}
		return true; // aún por implementar
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
	 * getter Book
	 * @return Retorna Book
	 */
	public Book getBook() {
		return book;
	}


	/**
	 * setter Book
	 * @param book Recibe un parámetor de tipo Book.
	 */
	public void setBook(Book book) {
		this.book = book;
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
	 * @param client Recibe un objeto Client
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	/**
	 * Getter atributo username
	 * @return Retorna String username
	 */
	@JsonIgnore
	public String getUsername() {
		return this.client.getUsername();
	}	
	

	/**
	 * Método toString()
	 */
	@Override
	public String toString() {
		return "Order [id=" + id + ", startDate=" + startDate + ", active=" + active + "]";
	}


		
}
