package com.yzpocket.esg_showcase_app.generation.repository;

import com.yzpocket.esg_showcase_app.generation.domain.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
}