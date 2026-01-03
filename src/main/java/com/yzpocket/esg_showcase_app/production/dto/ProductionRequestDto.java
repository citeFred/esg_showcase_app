package com.yzpocket.esg_showcase_app.production.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductionRequestDto {
    private String title;
    private String summary;
    private String description;
    private String mainUrl;
    private String subUrl;

    private Long teamId;
    private Long programId;
    private Long companyId;
}