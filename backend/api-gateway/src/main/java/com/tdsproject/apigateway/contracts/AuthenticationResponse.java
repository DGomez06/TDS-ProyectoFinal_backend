package com.tdsproject.apigateway.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AuthenticationResponse(
        String token
) {
}
