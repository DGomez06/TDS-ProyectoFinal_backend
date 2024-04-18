package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.DTO.OwnerDTO;
import com.tdsproject.apigateway.contracts.PasswordRequest;
import com.tdsproject.apigateway.contracts.RegisterRequest;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.services.JwtService;
import com.tdsproject.apigateway.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PutMapping
    public ResponseEntity<OwnerDTO> updateUser(
            HttpServletRequest servletRequest,
            @RequestBody OwnerDTO user
            ){
        String token = servletRequest.getHeader("Authorization").substring(7);
        var usrID = jwtService.extractUserId(token);

        return ResponseEntity.ok(userService.updateUser(Integer.parseInt(usrID), user));
    }

    @PostMapping
    public ResponseEntity<String> updatePassword(
            HttpServletRequest servletRequest,
            @RequestBody PasswordRequest passwordRequest
            ){
        String token = servletRequest.getHeader("Authorization").substring(7);
        var usrID = jwtService.extractUserId(token);
        userService.updaterPassword(Integer.parseInt(usrID), passwordRequest);

        return ResponseEntity.ok("Success!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            HttpServletRequest servletRequest
    ){
        String token = servletRequest.getHeader("Authorization").substring(7);
        var usrID = jwtService.extractUserId(token);
        userService.deleteUser(Integer.parseInt(usrID));

        return ResponseEntity.ok("Success!");
    }
}
