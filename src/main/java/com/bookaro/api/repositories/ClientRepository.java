package com.bookaro.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookaro.api.models.Client;
import com.bookaro.api.models.Order;
import com.bookaro.api.models.Subscription;

/**
 * 
 * @author Pedro<br>
 * Interface que actua como repositorio de la clase Client
 * Implementa el metodo findBySubscription para buscar por subscripcion
 */
@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findBySubscription (Subscription subscription);
	List<Order> findAllOrderByActive (String username);
	Client findClientByUsername (String username);
	
}
