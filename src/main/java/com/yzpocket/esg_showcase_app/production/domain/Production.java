package com.yzpocket.esg_showcase_app.production.domain;

import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.company.domain.Company;
import com.yzpocket.esg_showcase_app.mentor.domain.Mentor;
import com.yzpocket.esg_showcase_app.program.domain.Program;
import com.yzpocket.esg_showcase_app.file.domain.File;
import com.yzpocket.esg_showcase_app.team.domain.Team;
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
    private ProductionStatus productionStatus;

    @Column
    private String mainUrl;

    @Column
    private String subUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", unique = true)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    public Production(String title, String summary, String description, String mainUrl, String subUrl, Team team, Company company, Mentor mentor, Program program) {
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.productionStatus = ProductionStatus.DRAFT;
        this.mainUrl = mainUrl;
        this.subUrl = subUrl;
        this.team = team;
        this.company = company;
        this.mentor = mentor;
        this.program = program;
    }

    public void update(String title, String summary, String description, String mainUrl, String subUrl) {
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.mainUrl = mainUrl;
        this.subUrl = subUrl;
    }
}