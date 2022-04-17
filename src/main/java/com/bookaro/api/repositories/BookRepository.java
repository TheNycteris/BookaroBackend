package com.bookaro.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Book;

/**
 * 
 * @author Pedro<br>
 * Interface que hace la funcion de repositorio
 *
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	
	Book findByName (String name);

}
