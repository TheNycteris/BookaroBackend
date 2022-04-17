package com.bookaro.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Client;
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
	
}
