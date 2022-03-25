package com.bookaro.api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookaro.api.models.Subscription;
import com.bookaro.api.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	public ArrayList<Subscription> findAll(){
		return (ArrayList<Subscription>) subscriptionRepository.findAll();
	}
	
	public Optional<Subscription> findById (Long id) {
		return subscriptionRepository.findById(id);
	}
	
	public Subscription add (Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}
	
	public boolean update(Subscription subscription) {
	    try {
	    	subscriptionRepository.save(subscription);
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
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
