package com.tdsproject.apigateway.repositories;

import com.tdsproject.apigateway.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
