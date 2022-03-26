package com.bookaro.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookaro.api.models.UserModel;
import com.bookaro.api.services.UserService;

@RestController
@RequestMapping("/userregister")
public class RegisterUserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	//Register users
		@RequestMapping(value = "/userregister", method = RequestMethod.POST)
		public UserModel saveuserModel(@RequestBody UserModel us1) {
			us1.setPassword(encoder.encode(us1.getPassword()));
			return this.userService.saveUsermodel(us1);
		}

}
