package com.bookaro.api.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookaro.api.models.Book;
import com.bookaro.api.models.Image;

/**
 * Clase que hace la función de repositorio de la entidad Image
 * @author Pedro
 *
 */
@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
	Optional<Image> findByName(String name);
	Optional<Image> findByBook (Book book);
}
