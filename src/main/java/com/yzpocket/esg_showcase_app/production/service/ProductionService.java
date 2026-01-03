package com.yzpocket.esg_showcase_app.production.service;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.auth.domain.UserRole;
import com.yzpocket.esg_showcase_app.company.domain.Company;
import com.yzpocket.esg_showcase_app.company.repository.CompanyRepository;
import com.yzpocket.esg_showcase_app.mentor.domain.Mentor;
import com.yzpocket.esg_showcase_app.mentor.repository.MentorRepository;
import com.yzpocket.esg_showcase_app.production.domain.Production;
import com.yzpocket.esg_showcase_app.production.dto.ProductionRequestDto;
import com.yzpocket.esg_showcase_app.production.dto.ProductionResponseDto;
import com.yzpocket.esg_showcase_app.production.repository.ProductionRepository;
import com.yzpocket.esg_showcase_app.program.domain.Program;
import com.yzpocket.esg_showcase_app.program.repository.ProgramRepository;
import com.yzpocket.esg_showcase_app.team.domain.Team;
import com.yzpocket.esg_showcase_app.team.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final TeamRepository teamRepository;
    private final ProgramRepository programRepository;
    private final CompanyRepository companyRepository;
    private final MentorRepository mentorRepository;

    @Transactional
    public ProductionResponseDto createProduction(PrincipalDetails principalDetails, ProductionRequestDto productionRequestDto) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        // 각 엔티티 조회 및 유효성 검사 메서드 호출
        Program foundProgram = getValidProgram(productionRequestDto.getProgramId());
        Team foundTeam = getValidTeam(productionRequestDto.getTeamId());
        Company foundCompany = getValidCompany(productionRequestDto.getCompanyId());
        Mentor foundMentor = getValidMentor(productionRequestDto.getMentorId());

        // Production 객체 생성
        Production production = new Production(
                productionRequestDto.getTitle(),
                productionRequestDto.getSummary(),
                productionRequestDto.getDescription(),
                productionRequestDto.getMainUrl(),
                productionRequestDto.getSubUrl(),
                foundTeam,
                foundCompany,
                foundMentor,
                foundProgram
        );

        Production savedProduction = productionRepository.save(production);
        return new ProductionResponseDto(savedProduction);
    }

    public ProductionResponseDto getProduction(Long productionId) {
        Production production = findProductionById(productionId);
        return new ProductionResponseDto(production);
    }

    public List<ProductionResponseDto> getAllProductions() {
        return productionRepository.findAll().stream()
                .map(ProductionResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductionResponseDto updateProduction(Long productionId, ProductionRequestDto productionRequestDto, User user) {
        validateAdmin(user); // 관리자 권한 확인
        Production production = findProductionById(productionId);

        // Production 정보 업데이트
        production.update(
                productionRequestDto.getTitle(),
                productionRequestDto.getSummary(),
                productionRequestDto.getDescription(),
                productionRequestDto.getMainUrl(),
                productionRequestDto.getSubUrl()
        );
        return new ProductionResponseDto(production);
    }

    @Transactional
    public void deleteProduction(Long productionId, User user) {
        validateAdmin(user); // 관리자 권한 확인
        Production production = findProductionById(productionId);
        productionRepository.delete(production);
    }


    // --- 내부 조회 및 유효성 검사 메서드 ---
    private Production findProductionById(Long productionId) {
        return productionRepository.findById(productionId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 프로젝트를 찾을 수 없습니다: " + productionId));
    }

    private void validateAdmin(User user) {
        if (user.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new AccessDeniedException("관리자만 프로젝트를 생성, 수정, 삭제할 수 있습니다.");
        }
    }

    private Program getValidProgram(Long programId) {
        if (programId == null) {
            return null;
        }
        return programRepository.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException("해당 프로그램을 찾을 수 없습니다. ID: " + programId));
    }

    private Team getValidTeam(Long teamId) {
        if (teamId == null) {
            return null;
        }
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("해당 팀을 찾을 수 없습니다. ID: " + teamId));
    }

    private Company getValidCompany(Long companyId) {
        if (companyId == null) {
            return null;
        }
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 기업을 찾을 수 없습니다. ID: " + companyId));
    }

    private Mentor getValidMentor(Long mentorId) {
        if (mentorId == null) {
            return null;
        }
        return mentorRepository.findById(mentorId)
                .orElseThrow(() -> new EntityNotFoundException("해당 멘토를 찾을 수 없습니다. ID: " + mentorId));
    }
}