package com.toonsquare.esg_showcase_app.production.domain;

import com.toonsquare.esg_showcase_app.common.domain.TimeStamped;
import com.toonsquare.esg_showcase_app.company.domain.Company;
import com.toonsquare.esg_showcase_app.exhibition.domain.Exhibition;
import com.toonsquare.esg_showcase_app.file.domain.File;
import com.toonsquare.esg_showcase_app.team.domain.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "production")
@Entity
public class Production extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String summary;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductionStatus status;

    @Column
    private String promoUrl;

    @Column
    private String githubUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", unique = true, nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    public Production(String title, String summary, String description, String promoUrl, String githubUrl, Team team, Company company, Exhibition exhibition) {
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.status = ProductionStatus.DRAFT;
        this.promoUrl = promoUrl;
        this.githubUrl = githubUrl;
        this.team = team;
        this.company = company;
        this.exhibition = exhibition;
    }

    public void update(String title, String summary, String description, String promoUrl, String githubUrl) {
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.promoUrl = promoUrl;
        this.githubUrl = githubUrl;
    }
}