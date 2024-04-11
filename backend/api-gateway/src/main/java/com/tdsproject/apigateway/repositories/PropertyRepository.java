package com.tdsproject.apigateway.repositories;

import com.tdsproject.apigateway.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    @Query(value = "SELECT * FROM Property WHERE type = ':type%' AND rooms = :rooms AND price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    List<Property> getAllFiltered(
            @Param("type") String type,
            @Param("rooms") int rooms,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice
    );
}
