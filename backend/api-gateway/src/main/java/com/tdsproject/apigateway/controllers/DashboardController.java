package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.DTO.ContractDTO;
import com.tdsproject.apigateway.DTO.DashboardDTO;
import com.tdsproject.apigateway.DTO.OwnerDTO;
import com.tdsproject.apigateway.services.ContractService;
import com.tdsproject.apigateway.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<DashboardDTO> setContract(
            HttpServletRequest servletRequest
    ){
        return ResponseEntity.ok(contractService.getDashboard(servletRequest.getHeader("Authorization")));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<OwnerDTO>> getAllClients(
            HttpServletRequest servletRequest
    ){
        String token = servletRequest.getHeader("Authorization").substring(7);
        var usrID = jwtService.extractUserId(token);
        return ResponseEntity.ok(contractService.getAllClients(Integer.parseInt(usrID)));
    }
}
