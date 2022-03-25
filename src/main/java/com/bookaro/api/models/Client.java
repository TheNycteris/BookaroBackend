package com.bookaro.api.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@DiscriminatorValue( value="CL" )
public class Client extends User {
	
	@ManyToOne()
    @JoinColumn(name = "id_sub")
	private Subscription subscription;
	
	//private Integer subscription;

	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	/*public Client(Long id, String username, String password, String type, String name, String surname, String dni,
			String address, int age, int subscription) {
		super(id, username, password, type, name, surname, dni, address, age);
		this.subscription = subscription;
	}*/

	/*public int getSubscription() {
		return subscription;
	}

	public void setSubscription(int subscription) {
		this.subscription = subscription;
	}*/
	
	
	
	

}
