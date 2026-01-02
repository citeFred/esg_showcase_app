package com.yzpocket.esg_showcase_app.program.repository;

import com.yzpocket.esg_showcase_app.program.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}