package com.yzpocket.esg_showcase_app.team.service;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.auth.domain.UserRole;
import com.yzpocket.esg_showcase_app.auth.repository.UserRepository;
import com.yzpocket.esg_showcase_app.generation.domain.Generation;
import com.yzpocket.esg_showcase_app.generation.repository.GenerationRepository;
import com.yzpocket.esg_showcase_app.team.domain.Team;
import com.yzpocket.esg_showcase_app.team.dto.TeamRequestDto;
import com.yzpocket.esg_showcase_app.team.dto.TeamResponseDto;
import com.yzpocket.esg_showcase_app.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final GenerationRepository generationRepository;
    private final UserRepository userRepository;

    @Transactional
    public TeamResponseDto createTeam(PrincipalDetails principalDetails, TeamRequestDto requestDto) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Generation generation = findGenerationById(requestDto.getGenerationId());
        User leader = findUserById(requestDto.getLeaderUserId());

        Team team = new Team(
                requestDto.getName(),
                requestDto.getDescription(),
                generation,
                leader
        );

        Team savedTeam = teamRepository.save(team);
        return new TeamResponseDto(savedTeam);
    }

    public TeamResponseDto getTeam(Long teamId) {
        Team team = findTeamById(teamId);
        return new TeamResponseDto(team);
    }

    public List<TeamResponseDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TeamResponseDto updateTeam(Long teamId, TeamRequestDto requestDto, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Team team = findTeamById(teamId);
        team.update(
                requestDto.getName(),
                requestDto.getDescription()
        );
        // Generation, Leader 변경 로직은 필요 시 별도 구현
        return new TeamResponseDto(team);
    }
    
    @Transactional
    public TeamResponseDto approveTeam(Long teamId, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);
        
        Team team = findTeamById(teamId);
        team.approve();
        return new TeamResponseDto(team);
    }

    @Transactional
    public TeamResponseDto rejectTeam(Long teamId, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);
        
        Team team = findTeamById(teamId);
        team.reject();
        return new TeamResponseDto(team);
    }



    @Transactional
    public void deleteTeam(Long teamId, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Team team = findTeamById(teamId);
        teamRepository.delete(team);
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() ->
                new EntityNotFoundException("선택한 팀을 찾을 수 없습니다. ID: " + teamId)
        );
    }

    private Generation findGenerationById(Long generationId) {
        return generationRepository.findById(generationId).orElseThrow(() ->
                new EntityNotFoundException("선택한 기수를 찾을 수 없습니다. ID: " + generationId)
        );
    }
    
    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("선택한 사용자를 찾을 수 없습니다. ID: " + userId)
        );
    }

    private void validateAdmin(User user) {
        if (user.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new AccessDeniedException("관리자만 팀을 생성, 수정, 삭제, 승인할 수 있습니다.");
        }
    }
}