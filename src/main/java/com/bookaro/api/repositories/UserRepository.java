package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookaro.api.models.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
	UserModel findByUsername(String username);

}
