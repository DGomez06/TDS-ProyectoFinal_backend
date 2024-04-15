package com.tdsproject.apigateway.DTO;

import com.tdsproject.apigateway.entities.Favorite;

public record FavoriteDTO(
        Integer id,
        PropertyDTO property
) {

    public static FavoriteDTO get(Favorite favorites){
        return new FavoriteDTO(
                favorites.getId(),
                PropertyDTO.get(favorites.getProperty())
        );
    }
}
