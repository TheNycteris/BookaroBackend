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
	
	public ArrayList<User> findAll(){
		return (ArrayList<User>) userRepository.findAll();
	}
	
	public Optional<User> findById (Long id) {
		return userRepository.findById(id);
	}
	
	public User add (User user) {
		return userRepository.save(user);
	}
	
	public boolean update(User user) {
	    try {
	    	userRepository.save(user);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	public boolean delete (long id) {
		try {
	        userRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

}
