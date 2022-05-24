package com.bookaro.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookaro.api.models.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository <Editorial, Long> {}
