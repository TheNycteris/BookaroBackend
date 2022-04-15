package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.EmployeeRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@EnableMapRepositories
public class EmployeeService {
	
	@Autowired	
	EmployeeRepository employeeRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	//@PreAuthorize(value = "hasRole('ADMIN')")
	public ArrayList<Employee> findAll() {
		return (ArrayList<Employee>) employeeRepository.findAll();
	}
	
	public Optional<Employee> findById(Long id) {		
		return employeeRepository.findById(id);
	}
	
	public Employee add(Employee employee) {
		Employee copy = new Employee();
		copy.setAddress(employee.getAddress());
		copy.setAge(employee.getAge());
		copy.setDni(employee.getDni());
		copy.setName(employee.getName());		
		copy.setPassword(passwordEncoder.encode(employee.getPassword()));
		copy.setSurname(employee.getSurname());		
		copy.setUsername(employee.getUsername());
		copy.setPosition(employee.getPosition());
		copy.setSalary(employee.getSalary());
		copy.setEmail(employee.getEmail());		
		//copy.setRoles(employee.getRoles());	
		copy.setRole(employee.getRole());
		
		return employeeRepository.save(copy);
	}
	
	public boolean update(Employee employee) {
		try {
			employeeRepository.save(employee);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	public boolean delete (long id) {
		try {
			employeeRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

	
	
}
