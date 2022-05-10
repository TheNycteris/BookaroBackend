package com.bookaro.api.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/** 
 * @author Pedro <br>
 * 
 * Clase Client: 
 * <li> hace referencia a los usuario de tipo cliente </li>
 * <li> Hereda de User.</li>
 * <li> Es la clase con menos permisos.</li>
 * <li> El valor discriminatorio del tiepo de usuario es "CL".</li>
 * <li> Tiene vinculación con la clase Subscription y Order</li>
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@DiscriminatorValue( value="CL" )
public class Client extends User {
	
	// ******* Atributos de clase  *******
	
	@ManyToOne()    
	@JoinColumn(name = "id_sub")	
	private Subscription subscription;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch=FetchType.EAGER)	
	private List<Order> orders; 
	
	/**
	 * Constructor vacio
	 */
	public Client() {
		super();
		orders = new ArrayList<Order>(); 
	}	
	

	//  ******* Getter/Setter ******* 
	/**
	 * Getter que devuelve el tipo de subscripcion
	 * @return retorna un objeto de tipo Subscription
	 */
	public Subscription getSubscription() {
		return subscription;
	}

	/**
	 * Setter subscripcion
	 * @param subscription Recibe un parametro de Subscription con la subscripcion
	 */
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	
	/**
	 * Getter Order	  
	 * @return Devuelve una lista de tipo Order
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Setter Order
	 * @param orders Recibe una lista de tipo Order
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	

	/**
	 * Metodo toString
	 */
	@Override
	public String toString() {
		return "Client [subscription=" + subscription + ", orders=" + orders + ", getId()=" + getId()
				+ ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getName()=" + getName()
				+ "]";
	}
	
	
}
