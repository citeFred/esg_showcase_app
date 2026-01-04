package com.yzpocket.esg_showcase_app.team.controller;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.team.dto.TeamRequestDto;
import com.yzpocket.esg_showcase_app.team.dto.TeamResponseDto;
import com.yzpocket.esg_showcase_app.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDto> createTeam(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody TeamRequestDto requestDto) {
        TeamResponseDto responseDto = teamService.createTeam(principalDetails, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getTeam(@PathVariable Long teamId) {
        TeamResponseDto responseDto = teamService.getTeam(teamId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDto>> getAllTeams() {
        List<TeamResponseDto> responseDtos = teamService.getAllTeams();
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> updateTeam(
            @PathVariable Long teamId,
            @RequestBody TeamRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        TeamResponseDto responseDto = teamService.updateTeam(teamId, requestDto, principalDetails);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{teamId}/approve")
    public ResponseEntity<TeamResponseDto> approveTeam(
            @PathVariable Long teamId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        TeamResponseDto responseDto = teamService.approveTeam(teamId, principalDetails);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{teamId}/reject")
    public ResponseEntity<TeamResponseDto> rejectTeam(
            @PathVariable Long teamId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        TeamResponseDto responseDto = teamService.rejectTeam(teamId, principalDetails);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable Long teamId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        teamService.deleteTeam(teamId, principalDetails);
        return ResponseEntity.noContent().build();
    }
}