package com.bookaro.api.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.repositories.SubscriptionRepository;

/**
 * 
 * @author Pedro<br>
 * Clase que hace la funcion de Service. Implementa los siguientes metodos:
 * <li>List<Subscription> findAll()</li>
 * <li>Optional<Subscription> findById (Long id)</li>
 * <li>Subscription add (Subscription subscription)</li>
 * <li>Subscription update(Subscription subscription)</li>
 * <li>boolean delete (long id)</li> 
 * Inyecta la dependencia SubscriptionRepository
 *
 */
@Service
public class SubscriptionService {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;	


	/**
	 * Metodo que devuelve una lista de Subscription
	 * @return Retorna todas las subscripciones creadas
	 */
	//@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD', 'USER')")
	public List<Subscription> findAll(){
		return (List<Subscription>) subscriptionRepository.findAll();
	}
	
	
	/**
	 * Metodo que devulve una Subscription
	 * @param id Recibe un Long con el id de la subscripcion
	 * @return Retorna un objeto de tipo Subscription
	 */
	//@PostAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
	public Optional<Subscription> findById (Long id) {
		Optional<Subscription> aux = subscriptionRepository.findById(id);
		return aux;
	}
	
	
	/**
	 * Metodo para crear subscripciones
	 * @param subscription Recibe un objeto Subscription
	 * @return Retorna un objeto Subscription
	 */
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public Subscription add (Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}
	
	
	/**
	 * Metodo para actualizar una subscripcion
	 * @param subscription Recibe un objeto de tipo Subscription
	 * @return Retorna un objeto Subscription
	 */
	//@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD') or principal.equals(returnObject.get().getUsername())")
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MOD')")
	public Subscription update(Subscription subscription) {
	    try {
	    	subscriptionRepository.save(subscription);
	        return subscription;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return null;
	    }
	}
	
	
	/**
	 * Metodo para borrar una subscripcion
	 * @param id Recibe un long con el id de la subscripcion
	 * @return Retorna true o false en funcion de si ha podido o no borrarla.
	 */
	@PreAuthorize(value = "hasRole('ADMIN')")
	public boolean delete (long id) {
		try {
			subscriptionRepository.deleteById(id);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

}
