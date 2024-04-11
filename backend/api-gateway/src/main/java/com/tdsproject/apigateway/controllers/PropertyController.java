package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.contracts.PropertyRequest;
import com.tdsproject.apigateway.contracts.PropertyResponse;
import com.tdsproject.apigateway.services.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/property")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PostMapping
    public ResponseEntity<PropertyDTO> saveProperty(
            @RequestBody PropertyRequest request,
            HttpServletRequest servletRequest
    ){
        var property = service.save(request, servletRequest.getHeader("Authorization"));
        return ResponseEntity.ok(property);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<PropertyDTO>> getAllOwned(
            HttpServletRequest servletRequest
    ){
        var propertyDTOS = service.getAllOwned(servletRequest.getHeader("Authorization"));
        return ResponseEntity.ok(propertyDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<PropertyResponse> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer rooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ){
        return ResponseEntity.of(Optional.ofNullable(service.getAllProperties(
                Optional.ofNullable(page),
                rooms,
                bathrooms,
                type,
                address,
                Optional.ofNullable(minPrice),
                Optional.ofNullable(maxPrice)
        )));
    }

}
