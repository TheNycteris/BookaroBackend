package com.bookaro.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.User;

@Repository
public interface UserRepository extends /*CrudRepository*/JpaRepository<User, Long> {
	User findByUsername(String username);
}