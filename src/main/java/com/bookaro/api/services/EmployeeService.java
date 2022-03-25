package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public ArrayList<Employee> findAll() {
		return (ArrayList<Employee>) employeeRepository.findAll();
	}
	
	public Optional<Employee> findById(Long id) {		
		return employeeRepository.findById(id);
	}
	
	public Employee add(Employee employee) {		
		return employeeRepository.save(employee);
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
