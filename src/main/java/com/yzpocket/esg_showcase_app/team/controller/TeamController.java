//package com.yzpocket.esg_showcase_app.team.controller;
//
//import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
//import com.yzpocket.esg_showcase_app.team.dto.TeamRequestDto;
//import com.yzpocket.esg_showcase_app.team.dto.TeamResponseDto;
//import com.yzpocket.esg_showcase_app.team.service.TeamService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/teams")
//public class TeamController {
//
//    private final TeamService teamService;
//
//    @PostMapping
//    public ResponseEntity<TeamResponseDto> createTeam(
//            @RequestBody TeamRequestDto requestDto,
//            @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        TeamResponseDto responseDto = teamService.createTeam(requestDto, principalDetails.getUser());
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
//    }
//
//    @GetMapping("/{teamId}")
//    public ResponseEntity<TeamResponseDto> getTeam(
//            @PathVariable Long teamId) {
//        TeamResponseDto responseDto = teamService.getTeam(teamId);
//        return ResponseEntity.ok(responseDto);
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<List<TeamResponseDto>> getAllTeams() {
//        List<TeamResponseDto> responseDtoList = teamService.getAllTeams();
//        return ResponseEntity.ok(responseDtoList);
//    }
//
//    @PutMapping("/{teamId}")
//    public ResponseEntity<TeamResponseDto> updateTeam(
//            @PathVariable Long teamId,
//            @RequestBody TeamRequestDto requestDto,
//            @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        TeamResponseDto responseDto = teamService.updateTeam(teamId, requestDto, principalDetails.getUser());
//        return ResponseEntity.ok(responseDto);
//    }
//
//    @DeleteMapping("/{teamId}")
//    public ResponseEntity<Void> deleteTeam(
//            @PathVariable Long teamId,
//            @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        teamService.deleteTeam(teamId, principalDetails.getUser());
//        return ResponseEntity.noContent().build();
//    }
//}