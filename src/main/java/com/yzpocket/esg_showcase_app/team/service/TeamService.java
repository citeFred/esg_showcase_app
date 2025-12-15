//package com.yzpocket.esg_showcase_app.team.service;
//
//    import com.yzpocket.esg_showcase_app.auth.domain.User;
//    import com.yzpocket.esg_showcase_app.auth.domain.UserRole;
//    import com.yzpocket.esg_showcase_app.team.domain.Team;
//    import com.yzpocket.esg_showcase_app.team.dto.TeamRequestDto;
//    import com.yzpocket.esg_showcase_app.team.dto.TeamResponseDto;
//    import com.yzpocket.esg_showcase_app.team.repository.TeamRepository;
//    import jakarta.persistence.EntityNotFoundException;
//    import lombok.RequiredArgsConstructor;
//    import org.springframework.security.access.AccessDeniedException;
//    import org.springframework.stereotype.Service;
//    import org.springframework.transaction.annotation.Transactional;
//
//    import java.util.List;
//    import java.util.stream.Collectors;
//
//    @Service
//    @RequiredArgsConstructor
//    @Transactional(readOnly = true)
//    public class TeamService {
//
//        private final TeamRepository teamRepository;
//
//        @Transactional
//        public TeamResponseDto createTeam(TeamRequestDto requestDto, User user) {
//            validateAdmin(user); // 관리자 권한 확인
//            // Team 엔티티 생성 시 leaderName을 직접 사용
//            Team team = new Team(requestDto.getName(), requestDto.getDescription());
//            Team savedTeam = teamRepository.save(team);
//            return new TeamResponseDto(savedTeam);
//        }
//
//        public TeamResponseDto getTeam(Long teamId) {
//            Team team = findTeamById(teamId);
//            return new TeamResponseDto(team);
//        }
//
//        public List<TeamResponseDto> getAllTeams() {
//            return teamRepository.findAll().stream()
//                    .map(TeamResponseDto::new)
//                    .collect(Collectors.toList());
//        }
//
//        @Transactional
//        public TeamResponseDto updateTeam(Long teamId, TeamRequestDto requestDto, User user) {
//            validateAdmin(user); // 관리자 권한 확인
//            Team team = findTeamById(teamId);
//            // leaderName도 업데이트 되도록 수정
//            team.update(requestDto.getName(), requestDto.getDescription());
//            return new TeamResponseDto(team);
//        }
//
//        @Transactional
//        public void deleteTeam(Long teamId, User user) {
//            validateAdmin(user); // 관리자 권한 확인
//            Team team = findTeamById(teamId);
//            teamRepository.delete(team);
//        }
//
//        private Team findTeamById(Long teamId) {
//            return teamRepository.findById(teamId)
//                    .orElseThrow(() -> new EntityNotFoundException("해당 ID의 팀을 찾을 수 없습니다: " + teamId));
//        }
//
//        // 기존 validateTeamLeader 메서드를 관리자 권한 확인 메서드로 변경
//        private void validateAdmin(User user) {
//            if (user.getUserRole() != UserRole.ROLE_ADMIN) {
//                throw new AccessDeniedException("관리자만 팀을 생성, 수정, 삭제할 수 있습니다.");
//            }
//        }
//    }