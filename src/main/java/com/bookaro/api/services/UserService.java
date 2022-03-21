package com.bookaro.api.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookaro.api.models.User;
import com.bookaro.api.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public ArrayList<User> getUsers(){
		return (ArrayList<User>) userRepository.findAll();
	}

}
