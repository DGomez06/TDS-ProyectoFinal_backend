package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.contracts.PropertyRequest;
import com.tdsproject.apigateway.services.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("RESPONSE");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PropertyDTO>> filterProperties(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Integer minRooms,
        @RequestParam(required = false) Integer maxRooms,
        @RequestParam(required = false) Integer minBathrooms,
        @RequestParam(required = false) Integer maxBathrooms
) {
    List<PropertyDTO> filteredProperties = service.filterAndMapProperties(type, minPrice, maxPrice, minRooms, maxRooms, minBathrooms, maxBathrooms);
    return ResponseEntity.ok(filteredProperties);
}

}
