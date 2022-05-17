package com.bookaro.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookaro.api.models.Author;
import com.bookaro.api.models.Book;

/**
 * 
 * @author Pedro<br>
 * Interface que hace la funcion de repositorio para la entidad Book
 *
 */
@Repository
public interface BookRepository extends JpaRepository <Book, Long> {
	
	Book findBookByName (String name);
	//List<Book> findBookByAuthor (String author);
	List<Book> findBookByAuthor (Author author);
	List<Book> findBookByCategory (String category);
	List<Book> findBookByEditorial (String editorial);
	List<Book> findBookByActive (boolean active);

}
