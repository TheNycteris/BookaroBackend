package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
