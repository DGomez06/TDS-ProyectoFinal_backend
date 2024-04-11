package com.tdsproject.apigateway.DTO;

import jakarta.persistence.Column;

public record OwnerDTO(
        Integer Id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
