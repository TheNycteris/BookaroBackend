package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	public List<Subscription> findAll(){
		return (List<Subscription>) subscriptionRepository.findAll();
	}
	
	public Optional<Subscription> findById (Long id) {
		Optional<Subscription> aux = subscriptionRepository.findById(id);
		return aux;
	}
	
	public Subscription add (Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}
	
	public Subscription update(Subscription subscription) {
	    try {
	    	subscriptionRepository.save(subscription);
	        return subscription;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return null;
	    }
	}
	
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
