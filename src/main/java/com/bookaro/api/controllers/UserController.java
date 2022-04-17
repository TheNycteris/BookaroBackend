package com.bookaro.api.controllers;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.bookaro.api.security.JWTAuthenticationFilter;
import com.bookaro.api.security.SecurityConstants;
import com.bookaro.api.services.UserService;
import com.bookaro.api.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	private JWTAuthenticationFilter filter;
	
	
	
	@GetMapping("/email/{email}")
	public User findUserByEmail(@PathVariable("email") String email, Principal pri) {
		return service.findUserByEmail(email);
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> findAll() {	
		List<User> users = service.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);		
	}  
	
	
	@PostMapping("/header") 
	public void headerGet(HttpServletRequest request, HttpServletResponse response,  Authentication auth) throws IOException, ServletException {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		System.out.println("ESTE ES EL TOQUEN DEL CLIENTE" + token);
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
		
		//UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        //SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false)/*.setAuthentication(null)*/;
        
        authorities.remove(0);
        
        for (Cookie cookie: request.getCookies()) {
        	 String cookieName = cookie.getName();
             Cookie cookieToDelete = new Cookie(cookieName, null);
             cookieToDelete.setMaxAge(0);
             response.addCookie(cookieToDelete);
        }
        
		
		System.out.println(request.getHeader("Authorization")/*.getIntHeader("Authorization")*/);
		
		System.out.println(authentication.getName());
		
		
		
		//System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		//authentication.eraseCredentials();
	}
    
    
    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<User>> findByUsername(@PathVariable("username") String username, Principal pri) {
		//return service.findByUsername(username);
    	Optional<Optional<User>> user = Optional.of(service.findByUsername(username));  
        return ResponseEntity.of(user);       
	}



	@GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable("id") Long id, Principal pri) {
    	try {
			Optional<User> user = service.find(id);   
	        return ResponseEntity.of(user);
    	} catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }    	
    }

    @PostMapping("/insert")
    public ResponseEntity<User> create(@RequestBody User user) {
    	User created = service.create(user);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
    
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
    
    

    /*@DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
        //return ResponseEntity.accepted().build();
    }   */ 
    
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