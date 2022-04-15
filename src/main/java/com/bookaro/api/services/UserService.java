package com.bookaro.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.*;
import com.bookaro.api.repositories.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	//@PreAuthorize(value = "hasRole('ADMIN')")
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        Iterable<User> Users = repository.findAll();
        Users.forEach(list::add);
        return list;
    }
    
    @PostAuthorize(value = "hasRole('ADMIN') or principal.equals(returnObject.get().getUsername())")
    public Optional<User> findByUsername(String username) {
		//return repository.findByUsername(username);
		return Optional.of(repository.findByUsername(username));
	}


	@PostAuthorize(value = "hasRole('ADMIN') or principal.equals(returnObject.get().getUsername())")
    public Optional<User> find(Long id) {
        return repository.findById(id);
    }

    //@PreAuthorize(value = "hasRole('ADMIN')")
    public User create(User user) {
        // To ensure the User ID remains unique,
        // use the current timestamp.
    	List<String> roles = new ArrayList<>();
		//if (user.getRoles() == null) {
    	if (user.getRole() == null) {
			roles.add("ROLE_USER");
		} else {
			//roles = user.getRoles();
			roles.add(user.getRole());
		}
    	
    	User copy = new User(
                new Date().getTime(),
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getName(),
                user.getSurname(),
                user.getDni(),
                user.getAddress(),
                user.getEmail(),
                user.getAge(),
                //roles
                roles.get(0)
        );
        return repository.save(copy);
    }

    //@PreAuthorize(value = "hasRole('ADMIN')")
    public User update (User updatedUser) {
    	updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    	return repository.save(updatedUser);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    public void delete(Long id) {
        repository.deleteById(id);
    }

}