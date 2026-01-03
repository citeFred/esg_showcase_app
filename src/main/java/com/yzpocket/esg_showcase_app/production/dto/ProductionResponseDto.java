package com.yzpocket.esg_showcase_app.production.dto;

import com.yzpocket.esg_showcase_app.file.dto.FileResponseDto;
import com.yzpocket.esg_showcase_app.production.domain.Production;
import com.yzpocket.esg_showcase_app.production.domain.ProductionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProductionResponseDto {
    private Long id;
    private String title;
    private String summary;
    private String description;
    private ProductionStatus productionStatus;
    private String mainUrl;
    private String subUrl;

    // 연관 엔티티 정보 (ID 및 이름/제목)
    private Long teamId;
    private String teamName;

    private Long companyId;
    private String companyName;

    private Long programId;
    private String programTitle;

    // 첨부 파일 목록
    private List<FileResponseDto> files;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ProductionResponseDto(Production production) {
        this.id = production.getId();
        this.title = production.getTitle();
        this.summary = production.getSummary();
        this.description = production.getDescription();
        this.productionStatus = production.getProductionStatus();
        this.mainUrl = production.getMainUrl();
        this.subUrl = production.getSubUrl();

        // 필수 연관관계 (Team, Program)
        if (production.getTeam() != null) {
            this.teamId = production.getTeam().getId();
            this.teamName = production.getTeam().getName();
        }

        if (production.getProgram() != null) {
            this.programId = production.getProgram().getId();
            this.programTitle = production.getProgram().getTitle();
        }

        if (production.getCompany() != null) {
            this.companyId = production.getCompany().getId();
            this.companyName = production.getCompany().getName();
        }

        // 파일 리스트 변환
        if (production.getFiles() != null) {
            this.files = production.getFiles().stream()
                    .map(FileResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            this.files = Collections.emptyList();
        }

        this.createdAt = production.getCreatedAt();
        this.modifiedAt = production.getModifiedAt();
    }
}