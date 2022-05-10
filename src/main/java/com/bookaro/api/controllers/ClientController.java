package com.bookaro.api.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Order;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.services.ClientService;


/**
 * 
 * @author Pedro
 * Clase que hace la funcion de Controller para elmodelo Client<br>
 * Inyecta la dependencia ClientService
 *
 */
@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;	
	
		
	
	/**
	 * Metodo que recupera un Client por su username
	 * @param username Recibe String
	 * @return Retorna objeto Client.
	 */
	@GetMapping("/username/{username}")
	public Client findClientByUsername(@PathVariable ("username") String username) {
		return clientService.findClientByUsername(username);
	}


	/**
	 * Metodo que busca orders por id del cliente
	 * @param id Recibe long con el id del cliente
	 * @param pri Recibe Principal
	 * @return Retorna una lista de objetos Order
	 */
	@GetMapping("/orders/{id}")
	public List<Order> orders(@PathVariable ("id")Long id, Principal pri) {		
		Optional<Client> client = clientService.findById(id);
		List<Order> orders = client.get().getOrders();
		return orders;		
	}


	/**
	 * @author Pedro<br>
	 * Metodo que devuelve una lista de clientes por subscripcion
	 * @param subscription Rebie una objeto Subscription
	 * @return Retorna la lista de Clientes filtrada
	 */
	@GetMapping("/subscription")
	public List<Client> findBySubscription(@RequestBody Subscription subscription) {
		return clientService.findBySubscription(subscription);
	}

	
	/**
	 * @author Pedro<br>
	 * Metodo que devuelve una lista de clientes
	 * @return Retorna una lista de todos los clientes creados
	 */
	@GetMapping("/all")
	public ArrayList<Client> getAllClients(){
		return clientService.findAll();
	}
	
	
	/**
	 * @author Pedro<br>
	 * Metodo que devuelve un cliente por su id  
	 * @param id Recibe un long con el id del cliente
	 * @param pri Recibe un objeto Principal
	 * @return Retorna una objeto Client
	 */
	@GetMapping (value = "{id}")
	public Optional<Client> getClientId (@PathVariable ("id")long id, Principal pri) {
		return clientService.findById(id);
	}
	
	/**
	 * @author Pedro<br>
	 * Metodo para crear clientes
	 * @param client Recibe un objeto Cliente
	 * @return Retorna un String con el resultado.
	 */
	@PostMapping("/insert")
	public String addClient (@RequestBody Client client) {
		if (client != null) {
			clientService.add(client);
			return "Added a client";
		} else {
			return "Request does not contain a body";
		}
	}
	
	
	/**
	 * @author Pedro<br>
	 * Metodo para actualizar un cliente
	 * @param client Recibe un objeto Client
	 * @return Retorna un String en funcion del resultado.
	 */
	@PutMapping("/update")
	public String updateClient (@RequestBody Client client) {		
	    if(client != null) {
	    	clientService.update(client);
	        return "Updated Client.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	/**
	 * @author Pedro<br>
	 * Metodo para actualizar un cliente
	 * @param client Recibe un objeto Client
	 * @return Retorna un String en funcion del resultado.
	 */
	@PutMapping("/updateA")
	public String updateClientA (@RequestBody Client client) {
		
	    if(client != null) {
	    	clientService.updateA(client);
	        return "Updated Client.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	

	/**
	 * @author Pedro<br>
	 * Metodo para borrar un cliente por su id
	 * @param id Recibe un long con el id del cliente
	 * @return Retorna un String en funcion del resultado.
	 */
	@DeleteMapping("{id}")
	public String deleteClient (@PathVariable("id") long id) {
		if(id > 0) {			
			if(clientService.delete(id)) {
				return "Deleted the client.";
			} else {
				return "Cannot delete the client.";
			}
		}
		return "The id is invalid for the client.";
	}


	/**
	 * Metodo para dar de baja un cliente
	 * @param username Recibe un String con el username del cliente
	 * @return Retorna un String con el mensaje.
	 */
	@PutMapping("/baja/{username}")
	public String clientDown (@PathVariable("username") String username) {
		if (clientService.clientCancelation(username)) {
			return "Client dado de baja";
		} else {
			return "No se ha podido dar de baja el cliente: " + username;
		}	
		
	}	
	

}
