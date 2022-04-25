package com.bookaro.api.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
	Optional<Image> findByName(String name);
}
