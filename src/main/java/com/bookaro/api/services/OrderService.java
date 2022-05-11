package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Client;
import com.bookaro.api.models.Order;
import com.bookaro.api.repositories.OrderRepository;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Service para la clase Order<br>
 * Implementa los siguientes metodos:
 * <li>ArrayList<Order> findAll()</li>
 * <li>Optional<Order> findById (long id)</li>
 * <li>Order add (Order order)</li>
 * <li>boolean update(Order order)</li>
 * <li>boolean delete (long id)</li>
 * Inyecta la dependencia OrderRepository
 *
 */
@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	
	/**
	 * Método para ver las Orders de un cliente
	 * @param client Recibe un objeto Client
	 * @return Retorna una lista de objetos Order
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")	
	public List<Order> findAllOrderByClient(Client client) {
		return orderRepository.findAllOrderByClient(client);
	}

	/**
	 * Metodo que devuelve una lista de order
	 * @param active Recibe boolean 
	 * @return Retorna una lista de Order
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")	
	public List<Order> findAllOrderByActive(boolean active) {
		return orderRepository.findAllOrderByActive(active);
	}

	/**
	 * Metodo que devuelve una lista de Order
	 * @return Retorna todas las Orders
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public List<Order> findAll(){
		return (List<Order>) orderRepository.findAll();
	}
	
	/**
	 * Metodo que devuelve un objeto Order
	 * @param id Recibe un Long con el id de la Order
	 * @return Retorna un objeto Order
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")	
	public Optional<Order> findById (long id) {
		return orderRepository.findById(id);
	}
	
	
	/**
	 * Metodo que añade una Order 
	 * @param order Recibe una Order como parametro
	 * @return Retorna un objeto Order
	 */
	@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public Order add (Order order) {
		return orderRepository.save(order);
	}
	
	
	/**
	 * Metodo que actualiza una order
	 * @param order Recibe un objeto de tipo Order
	 * @return Retorna true o false en funcion de se se ha podido modificar o no.
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public boolean update(Order order) {
	    try {
	    	orderRepository.save(order);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	/**
	 * Metodo para borrar una Order
	 * @param id Recibe un Long con la id de la Order
	 * @return Retorna true o false en funcion de si se ha podido o no llevar a cabo la accion de borrado.
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean delete (long id) {
		try {
			orderRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

	
}
