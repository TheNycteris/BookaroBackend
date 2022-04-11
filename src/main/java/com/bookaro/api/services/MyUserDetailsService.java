package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookaro.api.models.User;
import com.bookaro.api.repositories.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + username +" not found");
		}  			
		List<String> roles = user.getRoles();
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		for(String role:roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}