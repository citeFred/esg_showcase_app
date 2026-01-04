package com.yzpocket.esg_showcase_app.program.dto;

import com.yzpocket.esg_showcase_app.program.domain.Program;
import com.yzpocket.esg_showcase_app.program.domain.ProgramStatus;
import lombok.Getter;

@Getter
public class ProgramResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final int year;
    private final ProgramStatus programStatus;

    public ProgramResponseDto(Program program) {
        this.id = program.getId();
        this.title = program.getTitle();
        this.description = program.getDescription();
        this.year = program.getYear();
        this.programStatus = program.getProgramStatus();
    }
}
