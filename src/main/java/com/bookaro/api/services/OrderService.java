package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Order;
import com.bookaro.api.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	public ArrayList<Order> findAll(){
		return (ArrayList<Order>) orderRepository.findAll();
	}
	
	public Optional<Order> findById (long id) {
		return orderRepository.findById(id);
	}
	
	public Order add (Order order) {
		return orderRepository.save(order);
	}
	
	public boolean update(Order order) {
	    try {
	    	orderRepository.save(order);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
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
