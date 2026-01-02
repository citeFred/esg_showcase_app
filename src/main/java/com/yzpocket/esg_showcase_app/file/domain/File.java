package com.yzpocket.esg_showcase_app.file.domain;

import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.company.domain.Company;
import com.yzpocket.esg_showcase_app.mentor.domain.Mentor;
import com.yzpocket.esg_showcase_app.production.domain.Production;
import com.yzpocket.esg_showcase_app.team.domain.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String storedFileName;

    @Column(nullable = false)
    private String filePath;

    @Column
    private Integer displayOrder;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_id")
    private Production production;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public File(String originalFileName, String storedFileName, String filePath, Integer displayOrder, FileType fileType, Production production, Company company, Mentor mentor, Team team) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.filePath = filePath;
        this.displayOrder = displayOrder;
        this.fileType = fileType;
        this.production = production;
        this.company = company;
        this.team = team;
        this.mentor = mentor;
    }
}