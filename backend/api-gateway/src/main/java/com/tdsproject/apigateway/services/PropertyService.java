package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.contracts.PropertyRequest;
import com.tdsproject.apigateway.entities.Property;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.repositories.PropertyRepository;
import com.tdsproject.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public PropertyDTO save(PropertyRequest propertyRequest, String authHeader){
        String token = authHeader.substring(7);

        var usrID = jwtService.extractUserId(token);

        User usr = usrRepository.findById(Integer.parseInt(usrID)).orElseThrow();

        Property property = new Property(
          usr,
          propertyRequest.address(),
          propertyRequest.size(),
          propertyRequest.rooms(),
          propertyRequest.bathrooms(),
          propertyRequest.price()
        );

        Property toMapped = repository.save(property);

        return new PropertyDTO(
                toMapped.getId(),
                toMapped.getAddress(),
                toMapped.getSize(),
                toMapped.getRooms(),
                toMapped.getBathrooms(),
                toMapped.getPrice()
        );
    }

    public List<Property> getAllProperties(){
        return repository.findAll();
    }

}
