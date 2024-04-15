package com.tdsproject.apigateway.repositories;

import com.tdsproject.apigateway.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
}