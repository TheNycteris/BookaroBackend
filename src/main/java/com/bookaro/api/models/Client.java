package com.bookaro.api.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@DiscriminatorValue( value="CL" )
public class Client extends User {
	
	@ManyToOne()
    @JoinColumn(name = "id_sub")
	private Subscription subscription;
	
	/*@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)	
	private List<Order> allOrders;*/
	@ManyToOne()
    @JoinColumn(name = "id_order")
	private Order order;	
	

	public Client() {		
		// TODO Auto-generated constructor stub
	}

	// Getter/Setter
	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/*public List<Order> getAllOrders() {
		return allOrders;
	}

	public void setAllOrders(List<Order> allOrders) {
		this.allOrders = allOrders;
	}*/
	
	
	
	
}
