package com.bookaro.api.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookaro.api.models.Employee;
import com.bookaro.api.models.User;
import com.bookaro.api.services.EmployeeService;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("")
	public ArrayList<Employee> getAllEmployee(){
		return employeeService.findAll();
	}
	
	@GetMapping (value = "{id}")
	public Optional<Employee> getEmployeeId (@PathVariable ("id")long id) {
		return employeeService.findById(id);
	}
	
	@PostMapping("")
	public String addEmployee (@RequestBody Employee employee) {
		if (employee != null) {
			employeeService.add(employee);
			return "Added a Empmloyee";
		} else {
			return "Request does not contain a body";
		}
	}
	
	@PutMapping("")
	public String updateEmployee (@RequestBody Employee employee) {
	    if(employee != null) {
	    	employeeService.update(employee);
	        return "Updated employee.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	@DeleteMapping("{id}")
	public String deleteEmployee (@PathVariable("id") long id) {
		if(id > 0) {
			if(employeeService.delete(id)) {
				return "Deleted the empmloyee.";
			} else {
				return "Cannot delete the employee.";
			}
		}
		return "The id is invalid for the employee.";
	}

	
	

}
