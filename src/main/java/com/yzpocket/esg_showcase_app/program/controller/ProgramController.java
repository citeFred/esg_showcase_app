package com.yzpocket.esg_showcase_app.program.controller;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.program.domain.ProgramStatus;
import com.yzpocket.esg_showcase_app.program.dto.ProgramRequestDto;
import com.yzpocket.esg_showcase_app.program.dto.ProgramResponseDto;
import com.yzpocket.esg_showcase_app.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping
    public ResponseEntity<ProgramResponseDto> createProgram(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody ProgramRequestDto requestDto) {
        ProgramResponseDto responseDto = programService.createProgram(principalDetails, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramResponseDto> getProgram(@PathVariable Long programId) {
        ProgramResponseDto responseDto = programService.getProgram(programId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponseDto>> getAllPrograms() {
        List<ProgramResponseDto> responseDtos = programService.getAllPrograms();
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramResponseDto> updateProgram(
            @PathVariable Long programId,
            @RequestBody ProgramRequestDto requestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ProgramResponseDto responseDto = programService.updateProgram(programId, requestDto, principalDetails);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{programId}/status")
    public ResponseEntity<ProgramResponseDto> updateProgramStatus(
            @PathVariable Long programId,
            @RequestParam("status") ProgramStatus status,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ProgramResponseDto responseDto = programService.updateProgramStatus(programId, status, principalDetails);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(
            @PathVariable Long programId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        programService.deleteProgram(programId, principalDetails);
        return ResponseEntity.noContent().build();
    }
}
