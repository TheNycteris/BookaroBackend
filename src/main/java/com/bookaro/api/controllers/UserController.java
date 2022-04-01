package com.bookaro.api.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.UserRepository;
import com.bookaro.api.services.UserService;

@RestController
//@RequestMapping("api/user")
public class UserController {
	
	/*@Autowired
	UserService userService;*/
	@Autowired
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserController (UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping("/users")
	public ArrayList<User> getAllUsers(){
		return (ArrayList<User>) userRepository.findAll();
	}
	
	//@GetMapping (value = "{id}")
	@GetMapping("/users/id/{id}")
	public Optional<User> getUserId (@PathVariable ("id")long id) {
		return userRepository.findById(id);
	}
	
	@GetMapping("/users/{username}")
	public User getUsuario (@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
	
	/*@PostMapping("")
	public String addUser (@RequestBody User user) {
		if (user != null) {
			userService.add(user);
			return "Added a user";
		} else {
			return "Request does not contain a body";
		}
	}*/
	
	

	@PostMapping("/users")
	public void saveUsuario(@RequestBody User usuario) {
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		userRepository.save(usuario);
	}
	
	@PutMapping("/users")
	public String updatePerson(@RequestBody User user) {
	    if(user != null) {
	    	//userService.update(user);
	    	userRepository.save(user);
	        return "Updated user.";
	    } else {
	        return "Request does not contain a body ";
	    }
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser (@PathVariable("id") long id) {
		userRepository.deleteById(id);
	}
	
	/*@DeleteMapping("/users/{id}")
	public String deleteUser (@PathVariable("id") long id) {
		if(id > 0) {
			if(userService.delete(id)) {
				return "Deleted the user.";
			} else {
				return "Cannot delete the user.";
			}
		}
		return "The id is invalid for the user.";
	}*/
	
	

}