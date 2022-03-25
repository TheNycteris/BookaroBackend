package com.bookaro.api.controllers;

import java.util.ArrayList;
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

import com.bookaro.api.models.Order;
import com.bookaro.api.services.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("")
	public ArrayList<Order> getAllUsers(){
		return orderService.findAll();
	}
	
	@GetMapping (value = "{id}")
	public Optional<Order> getOrderId (@PathVariable ("id")long id) {
		return orderService.findById(id);
	}
	
	@PostMapping("")
	public String addOrder (@RequestBody Order order) {
		if (order != null) {
			orderService.add(order);
			return "Added a order";
		} else {
			return "Request does not contain a body";
		}
	}
	
	@PutMapping("")
	public String updateOrder(@RequestBody Order order) {
	    if(order != null) {
	    	orderService.update(order);
	        return "Updated order.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	@DeleteMapping("{id}")
	public String deleteOrder (@PathVariable("id") long id) {
		if(id > 0) {
			if(orderService.delete(id)) {
				return "Deleted the order.";
			} else {
				return "Cannot delete the order.";
			}
		}
		return "The id is invalid for the order.";
	}
	

}
