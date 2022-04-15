package com.bookaro.api.models;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "subscription")
public class Subscription {

	@OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Client> allClients;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id_sub;
	
	private String type;
	private double price;
	
	public Subscription () {}
	
		
	public Subscription(List<Client> allClients, Long id_sub, String type, double price) {		
		this.allClients = allClients;
		this.id_sub = id_sub;
		this.type = type;
		this.price = price;
	}


	// Getters/Setters
	public List<Client> getAllClients() {
		return allClients;
	}
	
	public void setAllClients(List<Client> allClients) {
		this.allClients = allClients;
	}
	
	public Long getId() {
		return id_sub;
	}
	
	public void setId(Long id_sub) {
		this.id_sub = id_sub;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
		
}
