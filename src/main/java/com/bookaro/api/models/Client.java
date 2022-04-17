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
@Entity
@Table(name = "client")
@DiscriminatorValue( value="CL" )
public class Client extends User {
	
	// ******* Atributos de clase  *******
	
	@ManyToOne()
    @JoinColumn(name = "id_sub")
	private Subscription subscription;
	
	
	@ManyToOne()
    @JoinColumn(name = "id_order")
	private Order order;	
	
	/**
	 * Constructor vacio
	 */
	public Client() {}

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
	 * @return Devuelve un objeto de tipo Order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Setter Order
	 * @param order Recibe un parametro de tipo Order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	
	
	
	
	
}
