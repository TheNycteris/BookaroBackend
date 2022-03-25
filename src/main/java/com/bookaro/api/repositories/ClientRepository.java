package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

}
