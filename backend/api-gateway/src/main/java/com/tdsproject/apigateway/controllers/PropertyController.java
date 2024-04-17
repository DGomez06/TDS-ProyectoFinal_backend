package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.DTO.ContractDTO;
import com.tdsproject.apigateway.DTO.FavoriteDTO;
import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.contracts.NotifyRequest;
import com.tdsproject.apigateway.contracts.PropertyRequest;
import com.tdsproject.apigateway.contracts.PropertyResponse;
import com.tdsproject.apigateway.services.ContractService;
import com.tdsproject.apigateway.services.FavoriteService;
import com.tdsproject.apigateway.services.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/property")
public class PropertyController {

    @Autowired
    private PropertyService service;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ContractService contractService;

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

    @GetMapping("/rented")
    public ResponseEntity<List<PropertyDTO>> getAllRented(
            HttpServletRequest servletRequest
    ){
        var propertyDTOS = contractService.getAllRented(servletRequest.getHeader("Authorization"));
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

    @PutMapping("/{id}")
    public ResponseEntity<?> editProperty(@PathVariable Integer id, @Validated @RequestBody PropertyRequest propertyRequest) {
        try {
            var editedProperty = service.editProperty(id, propertyRequest);
            return ResponseEntity.ok(editedProperty);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al editar la propiedad: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Integer id) {
        try {
            service.deleteProperty(id);
            return ResponseEntity.ok("Propiedad eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la propiedad: " + e.getMessage());
        }
    }

    @PostMapping("/favorites/{id}")
    public ResponseEntity<FavoriteDTO> addFavorite(
            @PathVariable("id") Integer propertyId,
            HttpServletRequest servletRequest
    ){
        return ResponseEntity.ok(
                favoriteService.save(propertyId, servletRequest.getHeader("Authorization"))
        );
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites(HttpServletRequest servletRequest){
        return ResponseEntity.ok(favoriteService.getAllFavorites(servletRequest.getHeader("Authorization")));
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<String> deleteFavorite(@PathVariable("id") Integer favoriteId){
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.ok("DELETED");
    }


    @PostMapping("/notify/{id}")
    public ResponseEntity<String> notifyOwner(
            @PathVariable("id") Integer propertyId,
            @RequestBody NotifyRequest notifyToken,
            HttpServletRequest servletRequest
    ){
        return ResponseEntity.ok(contractService.notifyOwner(servletRequest.getHeader("Authorization"), propertyId, notifyToken));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<ContractDTO>> getAllFeed(
            HttpServletRequest servletRequest
    ){
        return ResponseEntity.ok(contractService.getFeed(servletRequest.getHeader("Authorization")));
    }

    @GetMapping("/contract/{id}")
    public ResponseEntity<String> setContract(
            @PathVariable("id") Integer feedId,
            HttpServletRequest servletRequest
    ){
        contractService.setContract(feedId);
        return ResponseEntity.ok("Success!");
    }
}
