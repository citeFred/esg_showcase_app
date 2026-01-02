package com.yzpocket.esg_showcase_app.program.domain;

import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.generation.domain.Generation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "program")
@Entity
public class Program extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProgramStatus programStatus;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Generation> generations = new ArrayList<>();

    public Program(String title, String description, int year, ProgramStatus programStatus) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.programStatus = ProgramStatus.PREPARING;
    }

    public void update(String title, String description, int year, ProgramStatus programStatus) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.programStatus = programStatus;
    }
}