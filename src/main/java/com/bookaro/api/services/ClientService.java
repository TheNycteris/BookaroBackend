package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Order;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.pagination.IClientService;
import com.bookaro.api.repositories.ClientRepository;


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
public class ClientService implements IClientService {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;		
		
	
	/**
	 * Metodo para buscar todos los clientes por paginacion
	 * @param pageNo Recibe un entero con el numero de pagina
	 * @param pageSize Recibe un entero con los elementos por pagina
	 * @return Retorna una lista de clientes.
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	@Override
	public List<Client> findPaginated(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Client> pagedResult = clientRepository.findAll(paging);
		return pagedResult.toList();
	}	
		
	
	/**
	 * Metodo que busca ordenes de un cliente por su estado
	 * @param username Recibe String con el username del cliente.
	 * @param active Recibe boolean true o false según el estatus de la order
	 * @return Retorna una lista de orders
	 */
	@PostAuthorize("hasAnyRole('ADMIN', 'MOD') or #username == authentication.name")
	public List<Order> findOrdersByActive(String username, boolean active) {
		Client client = clientRepository.findClientByUsername(username);
		List<Order> orders = new ArrayList<Order>();
		for (Order order: client.getOrders()) {
			if (order.isActive() == active) {
				orders.add(order);
			}
		}
		return orders;
	}

	/**
	 * Metodo que recupera un cliente por su username
	 * @param username Recibe un String con el username
	 * @return Retorna un objeto Client.
	 */
	@PostAuthorize("hasAnyRole('ADMIN', 'MOD') or #username == authentication.name")
	public Client findClientByUsername(String username) {
		return clientRepository.findClientByUsername(username);
	}	
	

	/**
	 * Método para buscar una lista de orders por el id del cliente
	 * @param idClient Recibe Long con el id del cliente
	 * @return Retorna una lista de Order
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
	public List<Order> orders(Long idClient) {
		Optional<Client> client = clientRepository.findById(idClient);
		List<Order> orders = client.get().getOrders();
		return orders;		
	}
	


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
		copy.setRole("ROLE_USER");		
		copy.setSubscription(client.getSubscription());				
		copy.setActive(client.isActive());
		return clientRepository.save(copy);
	}	
	
	
	/**
	 * Metodo para actualizar un cliente. Sólo podrá actualizar algunos datos puesto
	 * que tiene acceso el propio cliente.
	 * @param client Recibe un objeto Client
	 * @return Retorna true o false dependiendo del resultado.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'MOD') or #client.getUsername() == authentication.name")
	public boolean update (Client client) {
		Optional<Client> aux = clientRepository.findById(client.getId());
	    try {	    	
	    	client.setPassword(passwordEncoder.encode(client.getPassword()));
	    	client.setRole("ROLE_USER");
	    	client.setDni(aux.get().getDni());
	    	client.setId(aux.get().getId());
	    	client.setUsername(aux.get().getUsername());
	    	clientRepository.save(client);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	
	/**
	 * Metodo para actualizar un cliente. Solo tiene acceso ADMIN
	 * @param client Recibe un objeto Client
	 * @return Retorna true o false dependiendo del resultado.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	public boolean updateA (Client client) {		
	    try {
	    	// Capturamos el cliente de la BD
	    	Client aux = clientRepository.findClientByUsername(client.getUsername());
	    	// comprobamos que no sea null
	    	if (client.getPassword() == null) {
	    		client.setPassword(aux.getPassword());
	    	} else if (!aux.getPassword().equals(client.getPassword())) {	    		
	    		client.setPassword(passwordEncoder.encode(client.getPassword()));
	    	} else {
	    		client.setPassword(aux.getPassword());
	    	}	
	    	// Gruadamos el cliente	    	
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
	        return false;
	    }
	}
	
	
	/**
	 * Metodo para dar de baja un user por su username
	 * @param username Recibe un String con el username
	 * @return Retorna un boolean
	 */		
	@PreAuthorize("hasAnyRole('ADMIN', 'MOD') or #username == authentication.name")
	public boolean clientCancelation (String username) {
		try {			
			Client aux = clientRepository.findClientByUsername(username);			
			List<Order> orders = aux.getOrders();
			for (Order or: orders) {
				if (or.isActive()) {
					return false;
				}
			}						
			aux.setRole("ROLE_DOWN");
			aux.setActive(false);			
			clientRepository.save(aux);		
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
