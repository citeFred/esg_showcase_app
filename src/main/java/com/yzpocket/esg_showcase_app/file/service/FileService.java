package com.yzpocket.esg_showcase_app.file.service;

import com.yzpocket.esg_showcase_app.file.domain.File;
import com.yzpocket.esg_showcase_app.file.domain.FileType;
import com.yzpocket.esg_showcase_app.file.repository.FileRepository;
import com.yzpocket.esg_showcase_app.production.domain.Production;
import com.yzpocket.esg_showcase_app.production.repository.ProductionRepository; // 추가됨
import jakarta.persistence.EntityNotFoundException; // 추가됨
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final ProductionRepository productionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public void uploadProductionFile(Long productionId, MultipartFile multipartFile, FileType fileType) {
        // 1. productionId로 엔티티 조회
        Production production = productionRepository.findById(productionId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 프로젝트를 찾을 수 없습니다: " + productionId));

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 비어 있습니다.");
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storedFileName = UUID.randomUUID() + "_" + originalFileName;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            // 디렉토리가 없으면 생성하는 로직 추가 (안전성 확보)
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 디렉토리 생성에 실패했습니다: " + uploadPath.toString(), e);
        }

        Path filePath = uploadPath.resolve(storedFileName);

        try {
            multipartFile.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다: " + filePath.toString(), e);
        }

        // 2. 조회된 production 객체를 사용하여 File 엔티티 저장
        File fileEntity = new File(originalFileName, storedFileName, filePath.toString(), fileType, production);
        fileRepository.save(fileEntity);
    }
}