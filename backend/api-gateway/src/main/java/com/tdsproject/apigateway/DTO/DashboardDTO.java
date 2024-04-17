package com.tdsproject.apigateway.DTO;

public record DashboardDTO(
        Double totalIncome,
        Integer totalProperties,
        Integer totalTenants
) {
}
