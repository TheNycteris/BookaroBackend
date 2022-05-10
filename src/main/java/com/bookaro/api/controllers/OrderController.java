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
import com.bookaro.api.services.OrderService;


/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Controller para el modelo Order<br>
 * Inyecta la dependencia OrderService
 *
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;	
	
		
	/**
	 * Metodo para buscar las Orders de un cliente
	 * @param client Recibe objeto Client
	 * @param pri Recibe objet Principal
	 * @return Retorna lista de objetos Order
	 */
	@GetMapping("/client")
	public List<Order> findAllOrderByClient(@RequestBody Client client, Principal pri) {
		List<Order> todas = orderService.findAllOrderByClient(client);
		return todas;
	}

	
	/**
	 * Metodo que devuelve una lista de orders
	 * @param active Rebice boolean 
	 * @param pri Recibe objeto Principal
	 * @return Retorna una lista de orders
	 */
	@GetMapping (value = "/active/{active}")
	public List<Order> findAllOrderByActive(@PathVariable("active") boolean active, Principal pri) {		
		return orderService.findAllOrderByActive(active);
	}


	/**
	 * Metodo que devuelve una lista de orders
	 * @return Retorna una lista de order
	 */
	@GetMapping("/all")
	public ArrayList<Order> getAllOrders(){
		return orderService.findAll();
	}
	
	
	/**
	 * Metodo que obtiene un Order por su id
	 * @param id Recibe un long con el id de la Order
	 * @param pri Recibe un objeto Principal
	 * @return Retorna un objeto Order
	 */
	@GetMapping (value = "{id}")
	public Optional<Order> getOrderId (@PathVariable ("id")long id, Principal pri) {
		Optional<Order> order = orderService.findById(id);
		return order;
	}
	
	
	/**
	 * Metodo para añadir una Order
	 * @param order Recibe un objeto Order
	 * @param pri Recibe un objeto Principal
	 * @return Retorna un String con el resultado.
	 */
	@PostMapping("/insert")
	public String addOrder (@RequestBody Order order, Principal pri) {
		if (order != null) {	
			System.out.println(order.countActiveOrders(order.getClient()));
			if (order.countActiveOrders(order.getClient())) {
				orderService.add(order);
				return "Added a order";
			} else {
				return "Tiene demasiadas ordenes";
			}			
			
		} else {
			return "Request does not contain a body";
		}
	}
	
	/**
	 * Metodo para actualizar una order
	 * @param order Recibe un objeto Order
	 * @return Retorna un String con el resultado.
	 */
	@PutMapping("/update")
	public String updateOrder(@RequestBody Order order) {
	    if(order != null) {
	    	orderService.update(order);
	        return "Updated order.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	
	/**
	 * Metodo para borrar una Order por su ID
	 * @param id Recibe un long con el ID de la Order
	 * @return Retorna un String con el resultado.
	 */
	@DeleteMapping("{id}")
	public String deleteOrder (@PathVariable("id") long id) {
		if(id > 0) {
			if(orderService.delete(id)) {
				if (orderService.findById(id) != null ) {
					return "Cannot delete the order.";
				}
				return "Deleted the order.";
			} else {
				return "Cannot delete the order.";
			}
		}
		return "The id is invalid for the order.";
	}
	

}
