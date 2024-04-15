package com.tdsproject.apigateway.DTO;

import com.tdsproject.apigateway.entities.Contract;
import com.tdsproject.apigateway.entities.StatusEnum;

public record ContractDTO(
        Integer id,
        StatusEnum status,
        OwnerDTO client,
        PropertyDTO property
) {
    public static ContractDTO get(Contract contract){
        return new ContractDTO(
                contract.getId(),
                contract.getStatus(),
                OwnerDTO.get(contract.getClient()),
                PropertyDTO.get(contract.getProperty())
        );
    }
}
