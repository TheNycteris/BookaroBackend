package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

}
