package com.tdsproject.apigateway.contracts;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String phone
)
{ }
