package com.bookaro.api.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookaro.api.models.User;
import com.bookaro.api.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public ArrayList<User> getUsers(){
		return userService.getUsers();
	}
	
	@GetMapping (value = "{id}")
	public Optional<User> getUserId (@PathVariable ("id")long id) {
		return userService.getUserId(id);
	}
	
	@PostMapping("")
	public String addUser (@RequestBody User user) {
		if (user != null) {
			userService.insert(user);
			return "Added a user";
		} else {
			return "Request does not contain a body";
		}
	}
	
	@DeleteMapping("{id}")
	public String deleteUser (@PathVariable("id") long id) {
		if(id > 0) {
			if(userService.delete(id)) {
				return "Deleted the person.";
			} else {
				return "Cannot delete the person.";
			}
		}
		return "The id is invalid for the person.";
	}
	

}
