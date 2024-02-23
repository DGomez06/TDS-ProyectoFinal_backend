package com.tdsproject.apigateway.contracts;

import com.tdsproject.apigateway.entities.User;

public record PropertyRequest(
        String address,
        Integer size,
        Integer rooms,
        Integer bathrooms,
        Double price
) {
}