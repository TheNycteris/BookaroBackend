package com.bookaro.api.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bookaro.api.models.User;
import com.bookaro.api.security.SecurityConstants;
import com.bookaro.api.services.UserService;
import com.bookaro.api.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Controller para el modelo User.<br>
 * Implementa o inyecta las siguientes dependencias:
 * <li> UserService</li> 
 *
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;


	/**
	 * @author Pedro<br>
	 * Metodo para buscar un usuario por su email<br>
	 * @param email email Recibe un email
	 * @param pri Recibe un objeto de tipo Principal 
	 * @return etorna un objeto de tipo User
	 */
	@GetMapping("/email/{email}")
	public User findUserByEmail(@PathVariable("email") String email, Principal pri) {	
		return service.findUserByEmail(email);
	}



	/**
	 * @author Pedro<br>
	 * Metodo que devuelve una lista de usuarios	 
	 * @return Retorna todos los usuarios creados.
	 */
	@GetMapping("/all")
	public ResponseEntity<List<User>> findAll() {		
		List<User> users = service.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);		 
	}  


	/**
	 * @author Pedro<br>
	 * Metodo que gestiona logout del usuario. Almacena el token en una blackList.
	 * @param request parametro de tipo HttpServletRequest
	 * @return Retorna un String con el resultado.
	 */
	@PostMapping("/logout") 
	public String logout(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);		
		if (token != null) {			
			String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
					.build()
					.verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
					.getSubject();
			DecodedJWT jwt = JWT.decode(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
			Claim claim = jwt.getClaim("role");

			List<String> rolesList = claim.asList(String.class);;
			ArrayList<GrantedAuthority> authorities = new ArrayList<>();
			for(String role:rolesList) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

			if (!Utils.blackList(request)) {
				SecurityConstants.tokens.add(token);
				return "Hasta la próxima: " + authentication.getName();
			} else {
				return "Token invalidado o expirado";
			}
		} else {
			return "No ha incluido el token";
		}

	}


	/**
	 * @author Pedro<br>
	 * Metodo que devuelve un User a través de su "username"
	 * @param username Recibe un string con el username
	 * @param pri ecibe un objeto de tipo Principal	
	 * @return Retorna un objeto User
	 */
	@GetMapping("/username/{username}")
	public ResponseEntity<Optional<User>> findByUsername(@PathVariable("username") String username, Principal pri) {
		Optional<Optional<User>> user = Optional.of(service.findByUsername(username));  
		return ResponseEntity.of(user);       
	}


	/**
	 * @author Pedro<br>
	 * Metodo que busca un usuario por su id
	 * @param id Recibe un long con el id del usuario
	 * @param pri Recibe un objeto Principal	
	 * @return Retorna un objeto de tipo User
	 */	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") Long id, Principal pri) {
		try {
			Optional<User> user = service.findById(id);   
			return ResponseEntity.of(user);
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}


	/**
	 * Metodo para insertar o crear usuarios. Solo puede utilizarlo el admin
	 * @param user Recibe un objeto User	 
	 * @return Retorna un objeto User
	 */
	@PostMapping("/insertA")
	public ResponseEntity<User> createU(@RequestBody User user) {
		User created = service.create(user);		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(location).body(created);
	}


	/**
	 * Metodo para insertar usuarios. Puden utilizarlo:
	 * <li> ROL_ADMIN </li>
	 * <li> ROL_MOD </li>
	 * @param user Recibe un objeto User	 
	 * @return Retorna un objeto User
	 */
	@PostMapping("/insert")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.ok(service.createUser(user));
	}
	

	/**
	 * Metodo para actualizar un objeto User
	 * @param updatedUser Recibe un objeto de tipo user	 
	 * @return Retorna un objeto User
	 */
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {		
		return ResponseEntity.ok(service.updateUser(updatedUser));		
	}


	/**
	 * Metodo para borrar un usuario por su ID
	 * @param id Recibe un long con el id del usuario
	 * @return Retorn un String con el msg del resultado.
	 */
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {		
		if (service.deleteUser(id)) {
			return "Usuario borrado";
		} else {
			return "No se ha podido borrar el usuario";
		}		
	}


	/**
	 * Metodo para dar de baja un user por su username
	 * @param username Recibe un String con el username
	 * @return Retorna un String con el mensaje.
	 */
	@PutMapping("/baja/{username}")
	public String userDown(@PathVariable("username") String username) {		
		if (service.bajaUser(username)) {
			return "Usuario dado de baja";
		} else {
			return "No se ha podido dar de baja el usuario: " + username;
		}		
	}

}