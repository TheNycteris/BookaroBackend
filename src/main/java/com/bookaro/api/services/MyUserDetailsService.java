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

/**
 * 
 * @author Pedro<br>
 * <li>Clase que implementa la interface UserDetailService</li>
 * <li>Se utiliza para autorizr el usuario</li>
 * <li>Inyecta la dependencia UserRepository</li>
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
    UserRepository userRepo;

	/**
	 * Metodo para encontrar y autentificar el usuario
	 * @param username Recibe un parametro de tipo String con el nombre del usuario.
	 */
    @Override
    public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + username +" not found");
		}  			
		//List<String> roles = user.getRoles();
		
		List<String> roles = new ArrayList<String>();
		roles.add(user.getRole());
		
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		for(String role:roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}