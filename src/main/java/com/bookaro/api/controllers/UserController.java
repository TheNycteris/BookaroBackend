package com.bookaro.api.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookaro.api.models.UserModel;
import com.bookaro.api.services.UserService;

@RestController
@RequestMapping("/usermodel")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping()
	public ArrayList<UserModel> getUsers(){
		return userService.getUsers();
	}
	
	

}
