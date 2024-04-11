package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.DTO.OwnerDTO;
import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.contracts.PropertyRequest;
import com.tdsproject.apigateway.contracts.PropertyResponse;
import com.tdsproject.apigateway.entities.Property;
import com.tdsproject.apigateway.entities.StatusEnum;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.exception.ApiNotFoundException;
import com.tdsproject.apigateway.repositories.PropertyRepository;
import com.tdsproject.apigateway.repositories.UserRepository;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;
    @Autowired
    private UserRepository usrRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EntityManager entityManager;

    public PropertyDTO save(PropertyRequest propertyRequest, String authHeader) {
        String token = authHeader.substring(7);
        var usrID = jwtService.extractUserId(token);
        User usr = usrRepository.findById(Integer.parseInt(usrID)).orElseThrow();

        Property propertyToSave = new Property(
                usr,
                propertyRequest.address(),
                StatusEnum.AVAILABLE,
                propertyRequest.description(),
                propertyRequest.size(),
                propertyRequest.rooms(),
                propertyRequest.bathrooms(),
                propertyRequest.price(),
                propertyRequest.type(),
                propertyRequest.latitude(),
                propertyRequest.longitude()
        );

        Property property = repository.save(propertyToSave);

        return new PropertyDTO(
                property.getId(),
                property.getAddress(),
                property.getStatus(),
                property.getDescription(),
                property.getSize(),
                property.getRooms(),
                property.getBathrooms(),
                property.getPrice(),
                property.getType(),
                property.getLatitude(),
                property.getLongitude(),
                property.getImages(),
                new OwnerDTO(
                        property.getOwner().getId(),
                        property.getOwner().getFirstName(),
                        property.getOwner().getLastName(),
                        property.getOwner().getEmail(),
                        property.getOwner().getPhone()
                )
        );
    }

    public List<PropertyDTO> getAllOwned(String authHeader){
        String token = authHeader.substring(7);
        var usrID = jwtService.extractUserId(token);
        User usr = usrRepository.findById(Integer.parseInt(usrID)).orElseThrow();

        Property example = new Property();
        example.setOwner(usr);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Property> propertyList = repository.findAll(Example.of(example, matcher));
        List<PropertyDTO> propertyDTOS = new ArrayList<>();

        for (Property property : propertyList) {
            PropertyDTO propertyDTO = new PropertyDTO(
                    property.getId(),
                    property.getAddress(),
                    property.getStatus(),
                    property.getDescription(),
                    property.getSize(),
                    property.getRooms(),
                    property.getBathrooms(),
                    property.getPrice(),
                    property.getType(),
                    property.getLatitude(),
                    property.getLongitude(),
                    property.getImages(),
                    new OwnerDTO(
                            property.getOwner().getId(),
                            property.getOwner().getFirstName(),
                            property.getOwner().getLastName(),
                            property.getOwner().getEmail(),
                            property.getOwner().getPhone()
                    )
            );
            propertyDTOS.add(propertyDTO);
        }

        return propertyDTOS;
    }

    public PropertyDTO getById(Integer id) {
        Optional<Property> property = repository.findById(id);
        System.out.println("******************************************HOLA****************************************");

        if (property.isEmpty()) throw new ApiNotFoundException("Property not found with given id: " + id);

        return new PropertyDTO(
                property.get().getId(),
                property.get().getAddress(),
                property.get().getStatus(),
                property.get().getDescription(),
                property.get().getSize(),
                property.get().getRooms(),
                property.get().getBathrooms(),
                property.get().getPrice(),
                property.get().getType(),
                property.get().getLatitude(),
                property.get().getLongitude(),
                property.get().getImages(),
                    new OwnerDTO(
                            property.get().getOwner().getId(),
                            property.get().getOwner().getFirstName(),
                            property.get().getOwner().getLastName(),
                            property.get().getOwner().getEmail(),
                            property.get().getOwner().getPhone()
                    )
        );
    }

    public PropertyResponse getAllProperties(
            Optional<Integer> page,
            Integer rooms,
            Integer bathrooms,
            String propertyType,
            String address,
            Optional<Double> minPrice,
            Optional<Double> maxPrice
    ) {
        Property example = new Property();
        example.setType(propertyType);
        example.setRooms(rooms);
        example.setBathrooms(bathrooms);
        example.setAddress(address);
        example.setStatus(StatusEnum.AVAILABLE);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Pageable pageable = PageRequest.of(page.orElse(0), 20);

        Page<Property> propertyPage = repository.findAll(Example.of(example, matcher), pageable);

        List<PropertyDTO> propertyByPrice = new ArrayList<>();
        List<Property> properties = propertyPage.getContent();
        double price = 0;

        for (Property property : properties) {
            price = property.getPrice();
            if ((minPrice.isPresent() && price < minPrice.get()) ||
                    (maxPrice.isPresent() && price > maxPrice.get())) {
                continue; // Skip properties outside the price range
            }

            PropertyDTO propertyDTO = new PropertyDTO(
                    property.getId(),
                    property.getAddress(),
                    property.getStatus(),
                    property.getDescription(),
                    property.getSize(),
                    property.getRooms(),
                    property.getBathrooms(),
                    property.getPrice(),
                    property.getType(),
                    property.getLatitude(),
                    property.getLongitude(),
                    property.getImages(),
                    new OwnerDTO(
                            property.getOwner().getId(),
                            property.getOwner().getFirstName(),
                            property.getOwner().getLastName(),
                            property.getOwner().getEmail(),
                            property.getOwner().getPhone()
                    )
            );
            propertyByPrice.add(propertyDTO);
        }


        return new PropertyResponse(
                propertyPage.getNumber(),
                propertyPage.getTotalPages(),
                propertyByPrice
        );
    }
}
