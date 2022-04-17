package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.User;

/**
 * 
 * @author Pedro <br>
 * <li> Interfaz que hace la funcion de repositorio </li>
 * <li> Implementa los metodos findByUsername y findUserByEmail</li>
 */
@Repository
public interface UserRepository extends CrudRepository <User, Long> {
	
	User findByUsername(String username);	
	User findUserByEmail(String email);
	
}