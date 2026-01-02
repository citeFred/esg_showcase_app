package com.yzpocket.esg_showcase_app.company.repository;

import com.yzpocket.esg_showcase_app.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}