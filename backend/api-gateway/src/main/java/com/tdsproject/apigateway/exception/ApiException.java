package com.tdsproject.apigateway.exception;

import org.springframework.http.HttpStatus;

public record ApiException(
        String message,
        HttpStatus status
) { }
