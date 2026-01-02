package com.yzpocket.esg_showcase_app.production.controller;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.production.dto.ProductionRequestDto;
import com.yzpocket.esg_showcase_app.production.dto.ProductionResponseDto;
import com.yzpocket.esg_showcase_app.production.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productions")
@RequiredArgsConstructor
public class ProductionController {

    private final ProductionService productionService;

    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<ProductionResponseDto> createProduction(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody ProductionRequestDto productionRequestDto) {
        ProductionResponseDto responseDto = productionService.createProduction(principalDetails, productionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 프로젝트 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductionResponseDto>> getAllProductions() {
        List<ProductionResponseDto> responseDtoList = productionService.getAllProductions();
        return ResponseEntity.ok(responseDtoList);
    }

    // 프로젝트 단건 조회
    @GetMapping("/{productionId}")
    public ResponseEntity<ProductionResponseDto> getProduction(@PathVariable Long productionId) {
        ProductionResponseDto responseDto = productionService.getProduction(productionId);
        return ResponseEntity.ok(responseDto);
    }

    // 프로젝트 수정
    @PutMapping("/{productionId}")
    public ResponseEntity<ProductionResponseDto> updateProduction(
            @PathVariable Long productionId,
            @RequestBody ProductionRequestDto productionRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ProductionResponseDto responseDto = productionService.updateProduction(productionId, productionRequestDto, principalDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    // 프로젝트 삭제
    @DeleteMapping("/{productionId}")
    public ResponseEntity<Void> deleteProduction(
            @PathVariable Long productionId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        productionService.deleteProduction(productionId, principalDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}