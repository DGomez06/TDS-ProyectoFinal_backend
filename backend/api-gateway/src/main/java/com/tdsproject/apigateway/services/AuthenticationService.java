package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.contracts.AuthenticationRequest;
import com.tdsproject.apigateway.contracts.AuthenticationResponse;
import com.tdsproject.apigateway.contracts.RegisterRequest;
import com.tdsproject.apigateway.entities.PasswordResetTokenEntity;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tdsproject.apigateway.repositories.PasswordResetTokenRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    public AuthenticationResponse register(RegisterRequest request){
        User user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone(),
                passwordEncoder.encode(request.password())
        );
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            )
        );

        User user = repository.findByEmail(request.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    public void forgotPassword(String email) {
        String resetToken = generateResetToken(email);
        emailService.sendPasswordResetEmail(email, resetToken);
    }

    public void resetPassword(String token, String newPassword) {
        String email = validateResetToken(token);
        User user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }

    private String generateResetToken(String email) {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        String resetToken = String.valueOf(randomNumber);
        PasswordResetTokenEntity tokenEntity = new PasswordResetTokenEntity(email, resetToken, LocalDateTime.now().plusHours(1));
        passwordResetTokenRepository.save(tokenEntity);
        return resetToken;
    }

    public String validateResetToken(String token) {
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));
        if (tokenEntity.getExpiryDateTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }
        return tokenEntity.getEmail();
    }

}
