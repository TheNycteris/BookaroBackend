package com.bookaro.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookaro.api.models.Client;
import com.bookaro.api.models.Order;

/**
 * 
 * @author Pedro<br>
 * Interface que hace la funcion de repositorio.
 *
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	List<Order> findAllOrderByActive (boolean active);	
	List<Order> findAllOrderByClient (Client client);

}
