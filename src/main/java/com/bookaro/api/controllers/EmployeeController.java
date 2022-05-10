package com.bookaro.api.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
import com.bookaro.api.services.EmployeeService;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Controller para el modelo Employee.<br>
 * Inyecta la dependencia EmployeeService
 *
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	/**
	 * Metodo que muestra una lista de empleados por su posicion
	 * @param position Recibe un String con la posicion
	 * @return Retorna una lista de usuarios que cumplen con la posicion
	 */
	@GetMapping("/position/{position}")	
	public List<Employee> findEmployeesByPosition(@PathVariable("position")String position) {
		return employeeService.findEmployeesByPosition(position);
	}

	
	/**
	 * Metodo que devuelve una lista de empleados.
	 * @return Retorna todos los empleados registrados
	 */
	@GetMapping("/all")
	public ArrayList<Employee> getAllEmployee(){
		return employeeService.findAll();
	}
	
	
	/**
	 * Metodo que devuelve un empleado por su id
	 * @param id Recibe un long con el id del empleado
	 * @param pri Recibe un objeto Principal
	 * @return Retorna un objeto Employee
	 */
	@GetMapping (value = "{id}")
	public Optional<Employee> getEmployeeId (@PathVariable ("id")long id, Principal pri) {
		return employeeService.findById(id);
	}
	
	
	/**
	 * Metodo para crear un Employee
	 * @param employee Recibe un objeto de tipo Employee
	 * @return Retorna un String en funcion del resultado
	 */
	@PostMapping("/insert")
	public String addEmployee (@RequestBody Employee employee) {
		if (employee != null) {
			employeeService.add(employee);
			return "Added a Empmloyee";
		} else {
			return "Request does not contain a body";
		}
	}
	
	
	/**
	 * Metodo para actualizar un empleado
	 * @param employee Recibe un objeto Employee
	 * @return Retorna un String con el resultado
	 */
	@PutMapping("/update")
	public String updateEmployee (@RequestBody Employee employee) {		
	    if(employee != null) {	    	
	    	employeeService.update(employee);
	        return "Updated employee.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	/**
	 * Metodo para actualizar un empleado ROLE_ADMIN
	 * @param employee Recibe un objeto Employee
	 * @return Retorna un String con el resultado
	 */
	@PutMapping("/updateA")
	public String updateEmployeeA (@RequestBody Employee employee) {		
	    if(employeeService.updateA(employee)) {	    	
	        return "Updated employee.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	/**
	 * Metodo para borrar empleados por su id
	 * @param id Recibe un long con el id del empleado
	 * @return Retorna un String en funcion del resultado.
	 */
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
	
	
	/**
	 * Metodo para dar de baja un user por su username
	 * @param username Recibe un String con el username
	 * @return Retorna un String con el mensaje.
	 */
	@PutMapping("/baja/{username}")
	public String employeeDown(@PathVariable("username") String username) {		
		if (employeeService.bajaEmployee(username)) {
			return "Empleado dado de baja";
		} else {
			return "No se ha podido dar de baja el empleado: " + username;
		}		
	}
	

}
