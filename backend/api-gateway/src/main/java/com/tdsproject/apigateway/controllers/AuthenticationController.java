package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.contracts.AuthenticationRequest;
import com.tdsproject.apigateway.contracts.AuthenticationResponse;
import com.tdsproject.apigateway.contracts.RegisterRequest;
import com.tdsproject.apigateway.services.AuthenticationService;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;


@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody HashMap<String, String> emailMap){
        String email = emailMap.get("email");
        service.forgotPassword(email);
        return ResponseEntity.ok("Password reset email sent successfully");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody HashMap<String, String> passwordMap){
        String resetToken = passwordMap.get("resetToken");
        String newPassword = passwordMap.get("newPassword");
        service.resetPassword(resetToken, newPassword);
        return ResponseEntity.ok("Password reset successful");
}


}
