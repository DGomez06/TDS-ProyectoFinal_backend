package com.tdsproject.apigateway.repositories;

import com.tdsproject.apigateway.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
}
