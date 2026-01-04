package com.yzpocket.esg_showcase_app.generation.service;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.auth.domain.UserRole;
import com.yzpocket.esg_showcase_app.generation.domain.Generation;
import com.yzpocket.esg_showcase_app.generation.dto.GenerationRequestDto;
import com.yzpocket.esg_showcase_app.generation.dto.GenerationResponseDto;
import com.yzpocket.esg_showcase_app.generation.repository.GenerationRepository;
import com.yzpocket.esg_showcase_app.program.domain.Program;
import com.yzpocket.esg_showcase_app.program.repository.ProgramRepository;
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
public class GenerationService {

    private final GenerationRepository generationRepository;
    private final ProgramRepository programRepository;

    @Transactional
    public GenerationResponseDto createGeneration(PrincipalDetails principalDetails, GenerationRequestDto requestDto) {
        validateAdmin(principalDetails.getUser());

        Program program = findProgramById(requestDto.getProgramId());
        Generation generation = new Generation(requestDto.getTitle(), program);

        Generation savedGeneration = generationRepository.save(generation);
        return new GenerationResponseDto(savedGeneration);
    }

    public GenerationResponseDto getGeneration(Long generationId) {
        Generation generation = findGenerationById(generationId);
        return new GenerationResponseDto(generation);
    }

    public List<GenerationResponseDto> getAllGenerations() {
        return generationRepository.findAll().stream()
                .map(GenerationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public GenerationResponseDto updateGeneration(Long generationId, GenerationRequestDto requestDto, PrincipalDetails principalDetails) {
        validateAdmin(principalDetails.getUser());
        Generation generation = findGenerationById(generationId);

        generation.update(requestDto.getTitle());
        // Program을 변경하는 로직은 필요 시 별도로 구현
        
        return new GenerationResponseDto(generation);
    }

    @Transactional
    public void deleteGeneration(Long generationId, PrincipalDetails principalDetails) {
        validateAdmin(principalDetails.getUser());
        Generation generation = findGenerationById(generationId);
        generationRepository.delete(generation);
    }

    private Generation findGenerationById(Long generationId) {
        return generationRepository.findById(generationId).orElseThrow(() ->
                new EntityNotFoundException("선택한 기수를 찾을 수 없습니다. ID: " + generationId)
        );
    }

    private Program findProgramById(Long programId) {
        return programRepository.findById(programId).orElseThrow(() ->
                new EntityNotFoundException("선택한 프로그램을 찾을 수 없습니다. ID: " + programId)
        );
    }

    private void validateAdmin(User user) {
        if (user.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new AccessDeniedException("관리자만 기수를 생성, 수정, 삭제할 수 있습니다.");
        }
    }
}
