package com.tdsproject.apigateway.services;

public interface EmailService {
    void sendPasswordResetEmail(String email, String resetToken);
}
