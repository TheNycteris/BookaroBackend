package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Employee;
import com.bookaro.api.repositories.EmployeeRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la función de repositorio. Implementa los metodos:
 * <li> List<Employee> findEmployeesByPosition </li>
 * <li> ArrayList<Employee> findAll() </li>
 * <li> Optional<Employee> findById(Long id) </li>
 * <li> Employee add(Employee employee) </li>
 * <li> boolean update(Employee employee) </li>
 * <li> boolean delete (long id) </li> 
 * 
 * Inyecta las dependencias:
 * <li> EmployeeRepository </li>
 * <li> BCryptPasswordEncoder </li>
 *
 */
@Service
@EnableMapRepositories
public class EmployeeService {
	
	@Autowired	
	EmployeeRepository employeeRepository;
	
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	
	
	
	/**
	 * Metodo que devuelve una lista de empleados
	 * @param position Recibe la posicion en String
	 * @return Retorna la lista de empleados filtrando por su posicion
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public List<Employee> findEmployeesByPosition(String position) {
		return employeeRepository.findEmployeesByPosition(position);
	}

	/**
	 * Metodo que devuleve una lista de todos los empleados registrados
	 * @return Retorn ArrayList de Employee
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public ArrayList<Employee> findAll() {
		return (ArrayList<Employee>) employeeRepository.findAll();
	}
	
	
	/**
	 * Metodo que devuelve un empleado
	 * @param id Recibe un paramatro de tipo Long con el id de empleado
	 * @return Retorna el empleado
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
	public Optional<Employee> findById(Long id) {		
		return employeeRepository.findById(id);
	}
	
	/**
	 * Metodo que crea o añade un empleado
	 * @param employee Recibe un objeto de tipo Employee
	 * @return Retorna el usuario creado
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
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
		copy.setRole("ROLE_MOD");
		
		return employeeRepository.save(copy);
	}
	
	
	/**
	 * Metodo para actualizar un empleado
	 * @param employee Recibe un empleado por parametro
	 * @return Retorna true o false dependiendo del 
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean update(Employee employee) {
		try {
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
			employee.setRole("ROLE_MOD");
			employeeRepository.save(employee);			
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	@PreAuthorize(value = "hasRole('ADMIN')")
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
