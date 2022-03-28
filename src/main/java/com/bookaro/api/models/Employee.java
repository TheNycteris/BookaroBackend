package com.bookaro.api.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@DiscriminatorValue( value="EM" )
public class Employee extends User {

	private String role;
	private double salary;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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
	 * @param role
	 * @param salary
	 */
	public Employee(Long id, String username, String password, String type, 
			String name, String surname, String dni, String address, int age, String role, double salary) {
		super(id, username, password, type, name, surname, dni, address, age);
		this.role = role;
		this.salary = salary;
	}
	
	// Getter/Setter
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}	
	
		
}
