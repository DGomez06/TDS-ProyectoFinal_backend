package com.tdsproject.apigateway.DTO;

import com.tdsproject.apigateway.entities.User;
import jakarta.persistence.Column;

public record OwnerDTO(
        Integer Id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
    public static OwnerDTO get(User user){
        return new OwnerDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
