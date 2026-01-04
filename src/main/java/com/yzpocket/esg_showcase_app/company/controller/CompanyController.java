package com.yzpocket.esg_showcase_app.company.controller;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.company.dto.CompanyRequestDto;
import com.yzpocket.esg_showcase_app.company.dto.CompanyResponseDto;
import com.yzpocket.esg_showcase_app.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDto> createCompany(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody CompanyRequestDto requestDto) {
        CompanyResponseDto responseDto = companyService.createCompany(principalDetails, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDto> getCompany(@PathVariable Long companyId) {
        CompanyResponseDto responseDto = companyService.getCompany(companyId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> responseDtos = companyService.getAllCompanies();
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDto> updateCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        CompanyResponseDto responseDto = companyService.updateCompany(companyId, requestDto, principalDetails);
        return ResponseEntity.ok(responseDto);
    }



    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long companyId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        companyService.deleteCompany(companyId, principalDetails);
        return ResponseEntity.noContent().build();
    }
}
