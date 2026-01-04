package com.yzpocket.esg_showcase_app.program.service;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.auth.domain.UserRole;
import com.yzpocket.esg_showcase_app.program.domain.Program;
import com.yzpocket.esg_showcase_app.program.domain.ProgramStatus;
import com.yzpocket.esg_showcase_app.program.dto.ProgramRequestDto;
import com.yzpocket.esg_showcase_app.program.dto.ProgramResponseDto;
import com.yzpocket.esg_showcase_app.program.repository.ProgramRepository;
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
public class ProgramService {

    private final ProgramRepository programRepository;

    @Transactional
    public ProgramResponseDto createProgram(PrincipalDetails principalDetails, ProgramRequestDto requestDto) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);

        Program program = new Program(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getYear(),
                ProgramStatus.PREPARING
        );

        Program savedProgram = programRepository.save(program);
        return new ProgramResponseDto(savedProgram);
    }

    public ProgramResponseDto getProgram(Long programId) {
        Program program = findProgramById(programId);
        return new ProgramResponseDto(program);
    }

    public List<ProgramResponseDto> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(ProgramResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProgramResponseDto updateProgram(Long programId, ProgramRequestDto requestDto, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);
        Program program = findProgramById(programId);

        program.update(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getYear(),
                program.getProgramStatus()
        );
        return new ProgramResponseDto(program);
    }

    @Transactional
    public ProgramResponseDto updateProgramStatus(Long programId, ProgramStatus status, PrincipalDetails principalDetails) {
        User logginedUser = principalDetails.getUser();
        validateAdmin(logginedUser);
        Program program = findProgramById(programId);

        program.update(
                program.getTitle(),
                program.getDescription(),
                program.getYear(),
                status
        );
        return new ProgramResponseDto(program);
    }

    @Transactional
    public void deleteProgram(Long programId, PrincipalDetails principalDetails) {
        validateAdmin(principalDetails.getUser());
        Program program = findProgramById(programId);
        programRepository.delete(program);
    }

    private Program findProgramById(Long programId) {
        return programRepository.findById(programId).orElseThrow(() ->
                new EntityNotFoundException("선택한 프로그램을 찾을 수 없습니다. ID: " + programId)
        );
    }

    private void validateAdmin(User user) {
        if (user.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new AccessDeniedException("관리자만 프로그램을 생성, 수정, 삭제할 수 있습니다.");
        }
    }
}
