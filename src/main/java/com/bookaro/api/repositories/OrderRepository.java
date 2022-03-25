package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
