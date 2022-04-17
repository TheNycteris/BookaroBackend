package com.bookaro.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.*;
import com.bookaro.api.repositories.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de servicio o Service
 * <li> Inyecta las dependencias UserRepsiory y BCryptPasswordEncoder.</li>
 * <li> Se definen los siguientes metodos</li>
 * <ul>
 * 		<li> List<User> findAll() </li>
 * 		<li> User findUserByEmail(String email) </li>
 * 		<li> Optional<User> findByUsername(String username) </li>
 * 		<li> Optional<User> find(Long id) </li>
 * 		<li> User create(User user) </li>
 * 		<li> User update (User updatedUser) </li>
 * 		<li> void delete(Long id) </li>
 * </ul>
 *
 */
@Service
@EnableMapRepositories
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	/**
	 * Metodo que devuelve un usuario por su email
	 * @param email Recibe el email como String
	 * @return Retorna un objet User
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
	public User findUserByEmail(String email) {
		return repository.findUserByEmail(email);
	}

	/**
	 * Metodo que devuelve una lista de todos los usuarios registrados.
	 * @return Retorna una lista de usuarios.
	 */	
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Iterable<User> Users = repository.findAll();
        Users.forEach(list::add);
        return list;
    }
    
    /**
     * Metodo busca un usuario por su "username"
     * @param username Recibe el username por String
     * @return Retorna un objeto de tipo User o null en funcion de si encuentra o no el usuario
     */    
    @PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
    public Optional<User> findByUsername(String username) {
		//return repository.findByUsername(username);
		return Optional.of(repository.findByUsername(username));
	}


    /**
     * Metodo que busca un usuario por su id
     * @param id Recibe un Long con el id del usuario
     * @return Retorna User o null en funcion de si encuentra o no el usuario.
     */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
    public Optional<User> find(Long id) {
        return repository.findById(id);
    }

	/**
	 * Metodo para crear objetos de tipo User
	 * @param user Recibe un parametro de tipo User
	 * @return Retorna un objeto de tipo User.
	 */  
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
    public User create(User user) {
        
    	List<String> roles = new ArrayList<>();		
    	if (user.getRole() == null) {
			roles.add("ROLE_USER");
		} else {			
			roles.add(user.getRole());
		}
    	
    	User copy = new User(
                new Date().getTime(),
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getName(),
                user.getSurname(),
                user.getDni(),
                user.getAddress(),
                user.getEmail(),
                user.getAge(),                
                roles.get(0)
        );
        return repository.save(copy);
    }

    
    /**
     * Metodo que acualiza objetos de tipo User
     * @param updatedUser Recibe un parametro User
     * @return Retorna un objeto User
     */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
    public User update (User updatedUser) {
    	updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    	return repository.save(updatedUser);
    }

    
    /**
     * Metodo para eliminar usuarios
     * @param id Recibe un parametro de tipo Long con el ID del usuario
     */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
    public void delete(Long id) {
        repository.deleteById(id);
    }

}