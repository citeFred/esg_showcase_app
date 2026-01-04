package com.yzpocket.esg_showcase_app.team.dto;

import lombok.Getter;

@Getter
public class TeamRequestDto {
    private String name;
    private String description;
    private Long generationId;
    private Long leaderUserId;
}