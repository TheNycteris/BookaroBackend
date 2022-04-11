package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookaro.api.models.Client;
import com.bookaro.api.models.Employee;
import com.bookaro.api.models.User;
import com.bookaro.api.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public ArrayList<Client> findAll (){
		return (ArrayList<Client>) clientRepository.findAll();
	}
	
	public Optional<Client> findById(Long id) {
		return clientRepository.findById(id);
	}
	
	public Client add (Client client) {
		
		Client copy = new Client();
		copy.setAddress(client.getAddress());
		copy.setAge(client.getAge());
		copy.setDni(client.getDni());
		copy.setName(client.getName());		
		copy.setPassword(passwordEncoder.encode(client.getPassword()));
		copy.setSurname(client.getSurname());		
		copy.setUsername(client.getUsername());			
		copy.setEmail(client.getEmail());		
		copy.setRoles(client.getRoles());	
		copy.setSubscription(client.getSubscription());
		copy.setAllOrders(client.getAllOrders());
		
		return clientRepository.save(copy);
	}
	
	public boolean update (Client client) {
	    try {
	    	clientRepository.save(client);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	public boolean delete (long id) {
		try {
			clientRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

	
}
