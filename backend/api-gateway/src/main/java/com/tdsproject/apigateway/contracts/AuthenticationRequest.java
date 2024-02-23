package com.tdsproject.apigateway.contracts;

public record AuthenticationRequest(
        String email,
        String password
) {
}
