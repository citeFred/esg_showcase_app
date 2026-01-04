package com.yzpocket.esg_showcase_app.generation.controller;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.generation.dto.GenerationRequestDto;
import com.yzpocket.esg_showcase_app.generation.dto.GenerationResponseDto;
import com.yzpocket.esg_showcase_app.generation.service.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generations")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationService generationService;

    @PostMapping
    public ResponseEntity<GenerationResponseDto> createGeneration(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody GenerationRequestDto requestDto) {
        GenerationResponseDto responseDto = generationService.createGeneration(principalDetails, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{generationId}")
    public ResponseEntity<GenerationResponseDto> getGeneration(@PathVariable Long generationId) {
        GenerationResponseDto responseDto = generationService.getGeneration(generationId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<GenerationResponseDto>> getAllGenerations() {
        List<GenerationResponseDto> responseDtos = generationService.getAllGenerations();
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{generationId}")
    public ResponseEntity<GenerationResponseDto> updateGeneration(
            @PathVariable Long generationId,
            @RequestBody GenerationRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        GenerationResponseDto responseDto = generationService.updateGeneration(generationId, requestDto, principalDetails);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{generationId}")
    public ResponseEntity<Void> deleteGeneration(
            @PathVariable Long generationId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        generationService.deleteGeneration(generationId, principalDetails);
        return ResponseEntity.noContent().build();
    }
}
