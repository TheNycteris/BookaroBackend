package com.bookaro.api.controllers;


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
import com.bookaro.api.repositories.SubscriptionRepository;
import com.bookaro.api.services.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
	
	@Autowired
	SubscriptionService subscriptionService;
	
	
	@GetMapping("/all")
	public List<Subscription> getAllSubscription() {
		return subscriptionService.findAll();
	}
	
	@GetMapping (value = "{id}")
	public Optional<Subscription> getSubscriptionId (@PathVariable ("id") long id) {
		return subscriptionService.findById(id);
	}
	
	@PostMapping("")
	public String addSubscription (@RequestBody Subscription subscription) {
		if (subscription != null) {
			subscriptionService.add(subscription);
			return "Added a subscription";
		} else {
			return "Request does not contain a body";
		}
	}
	
	/*@PutMapping("")
	public String updateSubscription(@RequestBody Subscription subscription) {
	    if(subscription != null) {
	    	subscriptionService.update(subscription);
	        return "Updated subscription.";
	    } else {
	        return "Request does not contain a body";
	    }
	}*/
	
	@PutMapping("")
	public Boolean update(@RequestBody Subscription subscription) {
		try {
			subscriptionService.update(subscription);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
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
