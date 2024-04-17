package com.tdsproject.apigateway.services;

import com.tdsproject.apigateway.DTO.ContractDTO;
import com.tdsproject.apigateway.DTO.DashboardDTO;
import com.tdsproject.apigateway.DTO.PropertyDTO;
import com.tdsproject.apigateway.entities.Contract;
import com.tdsproject.apigateway.entities.Property;
import com.tdsproject.apigateway.entities.StatusEnum;
import com.tdsproject.apigateway.entities.User;
import com.tdsproject.apigateway.exception.ApiNotFoundException;
import com.tdsproject.apigateway.repositories.ContractRepository;
import com.tdsproject.apigateway.repositories.PropertyRepository;
import com.tdsproject.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private JwtService jwtService;

    public void notifyOwner(String authHeader, Integer propertyId){
        String token = authHeader.substring(7);
        var userId = jwtService.extractUserId(token);
        Optional<User> usr = userRepository.findById(Integer.parseInt(userId));
        Optional<Property> property = propertyRepository.findById(propertyId);

        if (property.isEmpty()) throw new ApiNotFoundException("Property not found with given id: "+ propertyId);
        if (usr.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);

        repository.save(new Contract(
                property.get().getOwner(),
                usr.get(),
                property.get()
        ));
    }

    public List<ContractDTO> getFeed(String authHeader){
        String token = authHeader.substring(7);
        var userId = jwtService.extractUserId(token);
        Optional<User> usr = userRepository.findById(Integer.parseInt(userId));

        if (usr.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);

        Contract example= new Contract();
        example.setOwner(usr.get());
        example.setStatus(StatusEnum.IN_PROCESS);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Contract> feedList = repository.findAll(Example.of(example, matcher));
        List<ContractDTO> feedDTOList = new ArrayList<>();

        for (Contract contract : feedList){
            feedDTOList.add(ContractDTO.get(contract));
        }

        return feedDTOList;
    }

    public void setContract(Integer contractId){
        Optional<Contract> contract = repository.findById(contractId);

        if (contract.isEmpty()) throw new ApiNotFoundException("Contract not found with given id: "+ contractId);

        contract.get().setStatus(StatusEnum.IN_CONTRACT);
        contract.get().getProperty().setStatus(StatusEnum.IN_CONTRACT);

        propertyRepository.save(contract.get().getProperty());
        repository.save(contract.get());
    }

    public DashboardDTO getDashboard(String authHeader){
        String token = authHeader.substring(7);
        var userId = jwtService.extractUserId(token);
        Optional<User> usr = userRepository.findById(Integer.parseInt(userId));

        if (usr.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);

        Contract example= new Contract();
        example.setOwner(usr.get());
        example.setStatus(StatusEnum.IN_CONTRACT);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Contract> contracts = repository.findAll(Example.of(example, matcher));
        Double totalIncome = 0.00;

        if(contracts.isEmpty()) return new DashboardDTO(
                totalIncome,
                0,
                0
        );

        for (Contract contract : contracts){
            totalIncome += contract.getProperty().getPrice();
        }

        return new DashboardDTO(
                totalIncome,
                contracts.size(),
                contracts.size()
        );
    }

    public List<PropertyDTO> getAllRented(String authHeader){
        String token = authHeader.substring(7);
        var userId = jwtService.extractUserId(token);
        Optional<User> usr = userRepository.findById(Integer.parseInt(userId));

        if (usr.isEmpty()) throw new ApiNotFoundException("User not found with given id: "+ userId);

        Contract example= new Contract();
        example.setClient(usr.get());
        example.setStatus(StatusEnum.IN_CONTRACT);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Contract> feedList = repository.findAll(Example.of(example, matcher));
        List<PropertyDTO> propertyDTOS = new ArrayList<>();

        for (Contract contract : feedList){
            propertyDTOS.add(PropertyDTO.get(contract.getProperty()));
        }

        return propertyDTOS;
    }
}