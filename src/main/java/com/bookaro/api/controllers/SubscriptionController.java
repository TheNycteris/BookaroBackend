package com.bookaro.api.controllers;


import java.security.Principal;
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
import com.bookaro.api.models.Subscription;
import com.bookaro.api.services.SubscriptionService;


/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Controller para el modelo Subscription<br>
 * Inyecta la dependencia SubscriptionService
 *
 */
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
	
	@Autowired
	SubscriptionService subscriptionService;
	
	/**
	 * Metodo que devuelve una lista de subscripciones
	 * @return Retorna un lista de todas las subscripciones
	 */
	@GetMapping("/all")
	public List<Subscription> getAllSubscription() {
		return subscriptionService.findAll();
	}
	
	/**
	 * Metodo que busca una subscripcion por su id
	 * @param id Recibe un long con el id de la subscripcion
	 * @param pri Recibe un objeto de tipo Principal
	 * @return Retorna un objeto de tipo Subscription
	 */
	@GetMapping (value = "{id}")
	public Optional<Subscription> getSubscriptionId (@PathVariable ("id") long id, Principal pri) {
		return subscriptionService.findById(id);
	}
	
	
	/**
	 * Metodo para crear subscripciones
	 * @param subscription Recibe un objeto de tipo Subscription
	 * @return Retorna un String con el resultado.
	 */
	@PostMapping("/insert")
	public String addSubscription (@RequestBody Subscription subscription) {
		if (subscription != null) {
			subscriptionService.add(subscription);
			return "Added a subscription";
		} else {
			return "Request does not contain a body";
		}
	}
	
	/**
	 * Metodo para actualiar una subscripcion
	 * @param subscription Recibe un objeto Subscription
	 * @return Retorna un String con el resultado.
	 */
	@PutMapping("/update")
	public String updateSubscription(@RequestBody Subscription subscription) {
	    if(subscription != null) {
	    	subscriptionService.update(subscription);
	        return "Updated subscription.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	
	/**
	 * Metodo para eliminar una subscripcion por su id
	 * @param id Recibe un long con el id de la subscripcion
	 * @return Retorna un String con el resultado.
	 */
	@DeleteMapping("{id}")
	public String deleteSubscription (@PathVariable("id") long id) {
		if(id > 0) {
			if(subscriptionService.delete(id)) {
				return "Deleted the subscription.";
			} else {
				return "Cannot delete the subscription.";
			}
		}
		return "The id is invalid for the subscription.";
	}

}
