package com.yzpocket.esg_showcase_app.company.service;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.auth.domain.UserRole;
import com.yzpocket.esg_showcase_app.company.domain.Company;
import com.yzpocket.esg_showcase_app.company.dto.CompanyRequestDto;
import com.yzpocket.esg_showcase_app.company.dto.CompanyResponseDto;
import com.yzpocket.esg_showcase_app.company.repository.CompanyRepository;
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
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyResponseDto createCompany(PrincipalDetails principalDetails, CompanyRequestDto requestDto) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Company company = new Company(
                requestDto.getName(),
                requestDto.getDescription()
        );

        Company savedCompany = companyRepository.save(company);
        return new CompanyResponseDto(savedCompany);
    }

    public CompanyResponseDto getCompany(Long companyId) {
        Company company = findCompanyById(companyId);
        return new CompanyResponseDto(company);
    }

    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompanyResponseDto updateCompany(Long companyId, CompanyRequestDto requestDto, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Company company = findCompanyById(companyId);
        company.update(
                requestDto.getName(),
                requestDto.getDescription()
        );
        return new CompanyResponseDto(company);
    }

    @Transactional
    public void deleteCompany(Long companyId, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Company company = findCompanyById(companyId);
        companyRepository.delete(company);
    }

    private Company findCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(() ->
                new EntityNotFoundException("선택한 기업을 찾을 수 없습니다. ID: " + companyId)
        );
    }

    private void validateAdmin(User user) {
        if (user.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new AccessDeniedException("관리자만 기업 정보를 생성, 수정, 삭제할 수 있습니다.");
        }
    }
}
