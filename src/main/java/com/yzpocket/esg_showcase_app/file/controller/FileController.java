package com.yzpocket.esg_showcase_app.file.controller;

import com.yzpocket.esg_showcase_app.auth.domain.PrincipalDetails;
import com.yzpocket.esg_showcase_app.file.domain.FileType;
import com.yzpocket.esg_showcase_app.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files") // 기본 경로: /api/files
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 프로젝트 파일 업로드
    @PostMapping("/production/{productionId}")
    public ResponseEntity<Void> uploadProductionFile(
            @PathVariable Long productionId,
            @RequestPart("file") MultipartFile file,
            @RequestParam("fileType") FileType fileType,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        fileService.uploadProductionFile(productionId, file, fileType);

        return ResponseEntity.ok().build();
    }
}