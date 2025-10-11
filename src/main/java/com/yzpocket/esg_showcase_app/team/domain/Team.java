package com.yzpocket.esg_showcase_app.team.domain;

import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.file.domain.File;
import com.yzpocket.esg_showcase_app.production.domain.Production;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id", nullable = false)
    private User leader;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamRegistration> teamRegistrations = new ArrayList<>();

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Production production;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();


    public Team(String name, String description, User leader) {
        this.name = name;
        this.description = description;
        this.leader = leader;
        this.status = ApprovalStatus.PENDING;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void approve() {
        this.status = ApprovalStatus.APPROVED;
    }

    public void reject() {
        this.status = ApprovalStatus.REJECTED;
    }
}