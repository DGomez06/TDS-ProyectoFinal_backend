package com.tdsproject.apigateway.contracts;

import com.tdsproject.apigateway.DTO.PropertyDTO;

import java.util.List;

public record PropertyResponse(
        Integer page,
        Integer totalPages,
        List<PropertyDTO> content
) {
}
