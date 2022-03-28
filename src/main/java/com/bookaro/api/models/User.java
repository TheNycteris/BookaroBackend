package com.bookaro.api.models;

import javax.persistence.*;

@Entity
@Table(name = "userbookaro")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="employeeClient" )
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	private String username, password, type, name, surname, dni, address;
	private int age;
	
	/**
	 * Constructor
	 * @param id
	 * @param username
	 * @param password
	 * @param type
	 * @param name
	 * @param surname
	 * @param dni
	 * @param address
	 * @param age
	 */
	public User (Long id, String username, String password, String type, 
			     String name, String surname, String dni, String address, int age) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.address = address;
		this.age = age;
	}
	
	public User() {
		
	}

	// Getter/Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	

}
