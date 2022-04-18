package com.bookaro.api.controllers;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	 * Metodo para buscar un usuario por su email
	 * @param email Recibe un email
	 * @param pri Recibe un objeto de tipo Principal
	 * @return Retorna un objeto de tipo User
	 */
	@GetMapping("/email/{email}")
	public User findUserByEmail(@PathVariable("email") String email, Principal pri) {
		return service.findUserByEmail(email);
	}

	
	/**
	 * Metodo que devuelve una lista de usuarios
	 * @return Retorna todos los usuarios creados.
	 * @throws IOException 
	 */
	@GetMapping("/all")
	public ResponseEntity<List<User>> findAll(HttpServletRequest request, HttpServletResponse res) throws IOException {		
		if (!Utils.blackList(request)) {
			List<User> users = service.findAll();
			if (users.isEmpty()) {
				return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
			}		
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} else {
			String body = "Sesion no validada o token expirado";
			res.getWriter().write(body);
			res.getWriter().flush();
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}		
	}  
	
	
	/**
	 * Meto para hacer un logout. Almacena el token en una blackList
	 * @param request parametro de tipo HttpServletRequest
	 * @return Retorna un String con el resultado.
	 */
	@PostMapping("/logout") 
	public String logout(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		System.out.println(token);
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
	 * Metodo que devuelve un User a través de su "username"
	 * @param username Recibe un string con el username
	 * @param pri Recibe un objeto de tipo Principal
	 * @return Retorna un objeto User
	 */
    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<User>> findByUsername(@PathVariable("username") String username, Principal pri) {
		//return service.findByUsername(username);
    	Optional<Optional<User>> user = Optional.of(service.findByUsername(username));  
        return ResponseEntity.of(user);       
	}


    /**
     * Metodo que busca un usuario por su id
     * @param id Recibe un long con el id del usuario
     * @param pri Recibe un Principal
     * @return Retorna un objeto de tipo User
     */
	@GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable("id") Long id, Principal pri) {
    	try {
			Optional<User> user = service.find(id);   
	        return ResponseEntity.of(user);
    	} catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }    	
    }

	
	/**
	 * Metodo para insertar usuarios
	 * @param user Recibe un objeto User
	 * @return Retorna un objeto User
	 */
    @PostMapping("/insert")
    public ResponseEntity<User> create(@RequestBody User user) {
    	User created = service.create(user);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
    
    
    /**
     * Metodo para actualizar un usuario
     * @param id Recibe un long con el id del usuario
     * @param patch Recibe un objeto JsonPatch
     * @return Retorna un objeto User
     */
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {    	
    	try {
            User user = service.find(id).orElseThrow(() -> new UsernameNotFoundException("User not found."));
            User patchedUser = service.update((User) Utils.applyPatch(patch, user));
            return ResponseEntity.ok(patchedUser);            
        } catch (JsonPatchException | JsonProcessingException e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }    	
    } 
    

    /**
     * Metodo para borrar un usuario    
     * @param id Recibe un long con el id del usuario.
     * @return Retorna un string dependiendo del resultado.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        try {
        	service.delete(id);
        	return "Delete User";
        } catch (Exception e) {
        	return "User not found";
        }
        
    }  	
   

}