package com.tdsproject.apigateway.contracts;

public record AuthenticationRequest(
        String Email,
        String Password
) {
}
