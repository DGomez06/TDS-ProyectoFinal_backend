package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.DTO.OwnerDTO;
import com.tdsproject.apigateway.contracts.PasswordRequest;
import com.tdsproject.apigateway.contracts.RegisterRequest;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.exception.ApiNotFoundException;
import com.tdsproject.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OwnerDTO getById(Integer id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ id);

        return new OwnerDTO(
          user.get().getId(),
          user.get().getFirstName(),
          user.get().getLastName(),
          user.get().getEmail(),
          user.get().getPhone()
        );
    }

    public OwnerDTO updateUser(Integer id, OwnerDTO userRequest){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ id);

        user.get().setFirstName(userRequest.firstName());
        user.get().setLastName(userRequest.lastName());
        user.get().setEmail(userRequest.email());
        user.get().setPhone(userRequest.phone());

        userRepository.save(user.get());

        return new OwnerDTO(
                user.get().getId(),
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getEmail(),
                user.get().getPhone()
        );
    }

    public void updaterPassword(Integer id, PasswordRequest passwordRequest){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ id);

        user.get().setPassword(passwordEncoder.encode(passwordRequest.password()));
        userRepository.save(user.get());
    }

    public void deleteUser(Integer id){
        if(!userRepository.existsById(id)) throw new ApiNotFoundException("User not found with given id: "+ id);
        userRepository.deleteById(id);
    }
}