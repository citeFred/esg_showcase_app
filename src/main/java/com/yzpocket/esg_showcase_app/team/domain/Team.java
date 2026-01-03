package com.yzpocket.esg_showcase_app.team.domain;

import com.yzpocket.esg_showcase_app.auth.domain.User;
import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.file.domain.File;
import com.yzpocket.esg_showcase_app.generation.domain.Generation;
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
    private TeamStatus teamStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id", nullable = false)
    private Generation generation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id", nullable = false)
    private User leader;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Production production;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
        this.teamStatus = TeamStatus.PENDING;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void approve() {
        this.teamStatus = TeamStatus.APPROVED;
    }

    public void reject() {
        this.teamStatus = TeamStatus.REJECTED;
    }
}