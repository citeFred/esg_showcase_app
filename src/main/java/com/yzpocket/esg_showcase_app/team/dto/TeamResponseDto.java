package com.yzpocket.esg_showcase_app.team.dto;

import com.yzpocket.esg_showcase_app.team.domain.Team;
import com.yzpocket.esg_showcase_app.team.domain.TeamStatus;
import lombok.Getter;

@Getter
public class TeamResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final TeamStatus teamStatus;
    private final Long generationId;
    private final String generationTitle;
    private final Long leaderId;
    private final String leaderName;

    public TeamResponseDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
        this.teamStatus = team.getTeamStatus();
        this.generationId = team.getGeneration().getId();
        this.generationTitle = team.getGeneration().getTitle();
        this.leaderId = team.getLeader().getId();
        this.leaderName = team.getLeader().getUsername();
    }
}