package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.contracts.AuthenticationRequest;
import com.tdsproject.apigateway.contracts.AuthenticationResponse;
import com.tdsproject.apigateway.contracts.RegisterRequest;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
