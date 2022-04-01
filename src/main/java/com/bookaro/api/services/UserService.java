package com.bookaro.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.*;
import com.bookaro.api.repositories.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Collections.emptyList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	public UserService (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<com.bookaro.api.models.User> findAll(){
		return (List<com.bookaro.api.models.User>) userRepository.findAll();
	}
	
	public Optional<com.bookaro.api.models.User> findById (Long id) {
		return userRepository.findById(id);
	}
	
	public com.bookaro.api.models.User add (com.bookaro.api.models.User user) {
		return userRepository.save(user);
	}
	
	public boolean update(com.bookaro.api.models.User user) {
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.bookaro.api.models.User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException (username);
		}
		return (UserDetails) new User (user.getUsername(), user.getPassword(), emptyList());
	}

	

}