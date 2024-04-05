package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.contracts.PropertyRequest;
import com.tdsproject.apigateway.entities.Property;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.repositories.PropertyRepository;
import com.tdsproject.apigateway.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Property property = new Property(
                usr,
                propertyRequest.address(),
                propertyRequest.size(),
                propertyRequest.rooms(),
                propertyRequest.bathrooms(),
                propertyRequest.price(),
                propertyRequest.type()
        );
        Property toMapped = repository.save(property);
        return new PropertyDTO(
                toMapped.getId(),
                toMapped.getAddress(),
                toMapped.getSize(),
                toMapped.getRooms(),
                toMapped.getBathrooms(),
                toMapped.getPrice(),
                toMapped.getType()
        );
    }

    public List<Property> getAllProperties() {
        return repository.findAll();
    }

    public List<Property> filterProperties(String propertyType, Double minPrice, Double maxPrice, Integer minRooms, Integer maxRooms, Integer minBathrooms, Integer maxBathrooms) {
        StringBuilder queryString = new StringBuilder("SELECT p FROM Property p WHERE 1=1");
        if (propertyType != null) {
            queryString.append(" AND p.type = :type");
        }
        if (minPrice != null) {
            queryString.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            queryString.append(" AND p.price <= :maxPrice");
        }
        if (minRooms != null) {
            queryString.append(" AND p.rooms >= :minRooms");
        }
        if (maxRooms != null) {
            queryString.append(" AND p.rooms <= :maxRooms");
        }
        if (minBathrooms != null) {
            queryString.append(" AND p.bathrooms >= :minBathrooms");
        }
        if (maxBathrooms != null) {
            queryString.append(" AND p.bathrooms <= :maxBathrooms");
        }

        Query query = entityManager.createQuery(queryString.toString(), Property.class);

        if (propertyType != null) {
            query.setParameter("type", propertyType);
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        if (minRooms != null) {
            query.setParameter("minRooms", minRooms);
        }
        if (maxRooms != null) {
            query.setParameter("maxRooms", maxRooms);
        }
        if (minBathrooms != null) {
            query.setParameter("minBathrooms", minBathrooms);
        }
        if (maxBathrooms != null) {
            query.setParameter("maxBathrooms", maxBathrooms);
        }

        List<Property> properties = query.getResultList();
        return properties;
    }

    public List<PropertyDTO> filterAndMapProperties(String propertyType, Double minPrice, Double maxPrice, Integer minRooms, Integer maxRooms, Integer minBathrooms, Integer maxBathrooms) {
        List<Property> filteredProperties = filterProperties(propertyType, minPrice, maxPrice, minRooms, maxRooms, minBathrooms, maxBathrooms);
        return filteredProperties.stream()
                .map(property -> new PropertyDTO(
                        property.getId(),
                        property.getAddress(),
                        property.getSize(),
                        property.getRooms(),
                        property.getBathrooms(),
                        property.getPrice(),
                        property.getType()
                ))
                .collect(Collectors.toList());
    }
}
