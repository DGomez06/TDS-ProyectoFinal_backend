package com.tdsproject.apigateway.contracts;

import com.tdsproject.apigateway.entities.User;

public record PropertyRequest(
        String address,
        String description,
        Integer size,
        Integer rooms,
        Integer bathrooms,
        Double price,
        String type,
        Double latitude,
        Double longitude
) {
}