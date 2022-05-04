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
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")	
	public User findUserByEmail(String email) {		
		User user = repository.findUserByEmail(email);		
		return user;		
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
		return Optional.of(repository.findByUsername(username));
	}


    /**
     * Metodo que busca un usuario por su id
     * @param id Recibe un Long con el id del usuario
     * @return Retorna User o null en funcion de si encuentra o no el usuario.
     */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
    public Optional<User> findById (Long id) {
        return repository.findById(id);
    }
	

	/**
	 * Metodo para crear objetos de tipo User.
	 * Está pensado para el usuario con ROLE_ADMIN
	 * @param user Recibe un parametro de tipo User
	 * @return Retorna un objeto de tipo User.
	 */	
	@PreAuthorize(value = "hasRole('ADMIN')")
    public User create(User user) {       
    			
    	if (user.getRole() == null) {			
    		user.setRole("ROLE_USER");
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
                user.getRole(),
                user.isActive()
        );
        return repository.save(copy);
    }

	/**
	 * Metodo para crear objetos de tipo User
	 * Está pensado para el ROLE_MOD
	 * @param user Recibe un parametro de tipo User
	 * @return Retorna un objeto de tipo User.
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
    public User createUser(User user) {    	
		user.setRole("ROLE_USER");
    	
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
                user.getRole(),
                user.isActive()
        );
        return repository.save(copy);
    }
    
    /**
     * Metodo que acualiza objetos de tipo User
     * @param updatedUser Recibe un parametro User
     * @return Retorna un objeto User
     */	
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
    public User updateUser (User updatedUser) {		
    	updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    	return repository.save(updatedUser);
    }
	
	
	/**
	 * Metodo para borrar un usuario por su ID
	 * @param id Recibi un long con el ID
	 * @return Retorna true o false
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean deleteUser (Long id) {
		try {
			repository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * Metodo para dar de baja un user por su username
	 * @param username Recibe un String con el username
	 * @return Retorna un String con el mensaje.
	 */		
	@PreAuthorize("hasAnyRole('ADMIN') or #username == authentication.name")
	public boolean bajaUser(String username) {
		try {			
			User aux = repository.findByUsername(username);
			
			if(aux instanceof Client) {
				List<Order> orders =((Client) aux).getOrders();
				for (Order or: orders) {
					if (or.isActive()) {
						return false;
					}
				}
			}
			
			aux.setRole("ROLE_DOWN");
			aux.setActive(false);			
			repository.save(aux);		
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}