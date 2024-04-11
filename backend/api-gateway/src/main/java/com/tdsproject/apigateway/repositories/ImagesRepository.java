package com.tdsproject.apigateway.repositories;

import com.tdsproject.apigateway.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
}
