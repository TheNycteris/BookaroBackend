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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * 
 * @author Pedro<br>
 * Clase Subscription:
 * <li> Gestiona las subscripciones de cada usuario. </li>
 * <li> Tiene vinculacion con las clase Client </li>
 *
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_sub")
@Entity
@Table(name = "subscription")
public class Subscription {

	// ***** Atributos de clase *****
	
	@OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	@JsonIgnore 
	private List<Client> allClients;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id_sub;	
	
	private String type;
	private double price;
	
	/**
	 * Constructor vacio
	 */
	public Subscription () {}	
	

	// ***** Getter/Setter *****
	
	/**
	 * Getter clients
	 * @return Retorna una lista de clientes.
	 */
	public List<Client> getAllClients() {
		return allClients;
	}
	
	/**
	 * Setter clients
	 * @param allClients Recibe una lista de clientes.
	 */
	public void setAllClients(List<Client> allClients) {
		this.allClients = allClients;
	}	
	
	/**
	 * Getter type
	 * @return Retorna un string con el tipo de subscripcion
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter type
	 * @param type Recibe un string con el tipo de subscripcion
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Getter price
	 * @return Retorna un double con el precio de la subscripcion
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Setter price
	 * @param price Recibe un double con el el precio de la subscripcion
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Getter id_sub
	 * @return retorna el id de la subscripcion "Long"
	 */
	public Long getId_sub() {
		return id_sub;		
	}

	/**
	 * Setter id_sub
	 * @param id_sub Recibe un Long con el id de la subscripcion
	 */
	public void setId_sub(Long id_sub) {
		this.id_sub = id_sub;		
	}
	
	
	
		
}
