package com.bookaro.api.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@DiscriminatorValue( value="EM" )
public class Employee extends User {

	private String position;
	private double salary;
	
	public Employee() {
		
	}

	public Employee(Long id, String username, String password, String name, 
			String surname, String dni, String address,
			String email, int age, List<String> roles, String position, double salary) {
		super(id, username, password, name, surname, dni, address, email, age, roles);
		this.position = position;
		this.salary = salary;
	}
	

	// Getter/Setter
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}	
	
	
		
}
