package com.bookaro.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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
public interface ClientRepository extends JpaRepository <Client, Long> {

	List<Client> findBySubscription (Subscription subscription);
	List<Order> findAllOrderByActive (String username);
	Client findClientByUsername (String username);
	
}
