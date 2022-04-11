package com.bookaro.api.controllers;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bookaro.api.models.User;
import com.bookaro.api.services.UserService;
import com.bookaro.api.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService service;

	
	@GetMapping("/all")
	public ResponseEntity<List<User>> findAll() {
		List<User> users = service.findAll();
		return ResponseEntity.ok().body(users);
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