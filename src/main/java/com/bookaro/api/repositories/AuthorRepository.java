package com.bookaro.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository <Author, Long> {

}
