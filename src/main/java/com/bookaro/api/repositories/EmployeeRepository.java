package com.bookaro.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Employee;

/**
 * 
 * @author Pedro <br>
 * 
 * Interface que hace la funcion de repositorio<br>
 * Implementa el metodo findEmployeesByPosito para buscar por posicion del empleado
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	List<Employee> findEmployeesByPosition(String position);
	Employee findEmployeeByUsername (String username);
}
