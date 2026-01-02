package com.yzpocket.esg_showcase_app.production.repository;

import com.yzpocket.esg_showcase_app.production.domain.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<Production, Long> {
}