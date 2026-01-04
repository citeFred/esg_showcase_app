package com.yzpocket.esg_showcase_app.generation.dto;

import com.yzpocket.esg_showcase_app.generation.domain.Generation;
import lombok.Getter;

@Getter
public class GenerationResponseDto {
    private final Long id;
    private final String title;
    private final Long programId;
    private final String programTitle;

    public GenerationResponseDto(Generation generation) {
        this.id = generation.getId();
        this.title = generation.getTitle();
        this.programId = generation.getProgram().getId();
        this.programTitle = generation.getProgram().getTitle();
    }
}
