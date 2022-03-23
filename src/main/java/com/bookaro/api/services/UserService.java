package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;

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
	
	public Optional<User> getUserId (Long id) {
		Optional<User> aux = userRepository.findById(id);
		if (aux.isPresent()) {
			return aux;
		} else {
			return null;
		}		
	}
	
	public User insert (User user) {
		return userRepository.save(user);
	}
	
	public boolean delete (long id) {
		try {
	        userRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
		 
		
		//userRepository.delete(user);
	}

}
