package com.bookaro.api.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	private Date startDate;
	private boolean active;
	
	/*@ManyToOne(fetch=FetchType.LAZY, optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "id_user")
	@JsonIgnore
	private Client client;*/
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Client> clients; //cambiar
	
	@OneToMany(mappedBy = "orderBook", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Book> books; //cambiar

	/**
	 * Métodeo para contar order activas
	 * @param clientId
	 * @return
	 */
	public boolean countActiveOrders (long clientId) {	
		return false; // 
	}
	
	// Getter/Setter
	public Long getOrderId() {
		return id;
	}

	public void setOrderId(Long orderId) {
		this.id = orderId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/*public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}*/

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	/////////////////
	
}
