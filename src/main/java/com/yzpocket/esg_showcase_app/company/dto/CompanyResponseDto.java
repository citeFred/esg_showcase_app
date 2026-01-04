package com.yzpocket.esg_showcase_app.company.dto;

import com.yzpocket.esg_showcase_app.company.domain.Company;
import lombok.Getter;

@Getter
public class CompanyResponseDto {
    private final Long id;
    private final String name;
    private final String description;

    public CompanyResponseDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
    }
}