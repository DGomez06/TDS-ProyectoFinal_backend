package com.tdsproject.apigateway.DTO;

public record PropertyDTO(
        Integer id,
        String address,
        Integer size,
        Integer rooms,
        Integer bathrooms,
        Double price,
        String type
) { }
