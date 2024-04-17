package com.tdsproject.apigateway.controllers;

import com.tdsproject.apigateway.DTO.ContractDTO;
import com.tdsproject.apigateway.DTO.DashboardDTO;
import com.tdsproject.apigateway.services.ContractService;
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

    @GetMapping
    public ResponseEntity<DashboardDTO> setContract(
            HttpServletRequest servletRequest
    ){
        return ResponseEntity.ok(contractService.getDashboard(servletRequest.getHeader("Authorization")));
    }
}
