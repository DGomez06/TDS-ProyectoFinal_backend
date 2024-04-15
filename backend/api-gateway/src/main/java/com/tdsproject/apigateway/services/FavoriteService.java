package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.DTO.FavoriteDTO;
import com.tdsproject.apigateway.DTO.OwnerDTO;
import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.entities.Favorite;
import com.tdsproject.apigateway.entities.Property;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.exception.ApiNotFoundException;
import com.tdsproject.apigateway.repositories.FavoriteRepository;
import com.tdsproject.apigateway.repositories.PropertyRepository;
import com.tdsproject.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PropertyRepository propertyRepository;

    public FavoriteDTO save(Integer propertyId, String authHeader){
        String token = authHeader.substring(7);
        var userId = jwtService.extractUserId(token);
        Optional<User> usr = userRepository.findById(Integer.parseInt(userId));

        Optional<Property> property = propertyRepository.findById(propertyId);

        if (property.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);
        if (usr.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);

        Favorite favorite = repository.save(new Favorite(
                usr.get(),
                property.get()
        ));

        return FavoriteDTO.get(favorite);
    }

    public void deleteFavorite(Integer favoriteId){
        repository.deleteById(favoriteId);
    }

    public List<FavoriteDTO> getAllFavorites(String authHeader){
        String token = authHeader.substring(7);
        var userId = jwtService.extractUserId(token);
        Optional<User> user = userRepository.findById(Integer.parseInt(userId));

        if (user.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);

        Favorite example = new Favorite();
        example.setUser(user.get());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Favorite> favoritesList = repository.findAll(Example.of(example, matcher));
        List<FavoriteDTO> favoriteDTOS = new ArrayList<>();

        for (Favorite favorite : favoritesList){
            favoriteDTOS.add(FavoriteDTO.get(favorite));
        }

        return favoriteDTOS;
    }
}