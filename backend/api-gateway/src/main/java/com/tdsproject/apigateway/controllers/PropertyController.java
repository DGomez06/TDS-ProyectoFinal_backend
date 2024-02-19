package com.tdsproject.apigateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/property")
public class PropertyController {

    @GetMapping
    public ResponseEntity<String> testRetreiving(){
        return ResponseEntity.ok("HELLOOOO!!");
    }
}
