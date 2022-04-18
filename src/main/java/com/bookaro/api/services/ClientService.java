package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.repositories.ClientRepository;
import com.bookaro.api.repositories.SubscriptionRepository;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Service. Implementa los siguiente metodos:
 * <li> List<Client> findBySubscription(Subscription subscription)<li>
 * <li> ArrayList<Client> findAll ()<li>
 * <li> Optional<Client> findById(Long id)<li>
 * <li> Client add (Client client)<li>
 * <li> boolean update (Client client)<li>
 * <li> boolean delete (long id)<li>
 *
 */
@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;	
	
	/**
	 * Metodo que devuelve una lista de clientes
	 * @param subscription Recibe un objeto Subscription
	 * @return Retorna una lista con los clientes de una determinada subscripcion
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")	
	public List<Client> findBySubscription(Subscription subscription) {
		return clientRepository.findBySubscription(subscription);
	}	
	
	/**
	 * Metodo que devuelve una lista de clientes
	 * @return Retorna todos los clientes registrados
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")	
	public ArrayList<Client> findAll (){
		return (ArrayList<Client>) clientRepository.findAll();
	}
	
	
	/**
	 * Metodo que devuelve un cliente
	 * @param id Recibe un Long con el id del cliente
	 * @return Retorna el cliente si lo encuentra
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
	public Optional<Client> findById(Long id) {
		return clientRepository.findById(id);
	}
	
	
	/**
	 * Metodo para crear clientes
	 * @param client Recibe un objeto de tipo Client
	 * @return Retorna el cliente
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")	
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
		copy.setRole(client.getRole());
		copy.setSubscription(client.getSubscription());
		copy.setOrder(client.getOrder());		
		
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
	
	
	/**
	 * Metodo para eliminar un cliente por su id
	 * @param id Recibe un parametro de tipo Long 
	 * @return Retorna true o false dependiendo de si se ha podido borrar el cliente.
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")	
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
