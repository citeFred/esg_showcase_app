package com.yzpocket.esg_showcase_app.file.repository;

import com.yzpocket.esg_showcase_app.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}