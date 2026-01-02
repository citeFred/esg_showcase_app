package com.yzpocket.esg_showcase_app.mentor.repository;

import com.yzpocket.esg_showcase_app.mentor.domain.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}