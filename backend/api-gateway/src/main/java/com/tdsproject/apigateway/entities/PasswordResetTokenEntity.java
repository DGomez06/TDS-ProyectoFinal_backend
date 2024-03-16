package com.tdsproject.apigateway.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class PasswordResetTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String token;
    private LocalDateTime expiryDateTime;

    public PasswordResetTokenEntity() {
    }

    public PasswordResetTokenEntity(String email, String token, LocalDateTime expiryDateTime) {
        this.email = email;
        this.token = token;
        this.expiryDateTime = expiryDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }
}
