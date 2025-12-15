package com.yzpocket.esg_showcase_app.generation.domain;

import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.program.domain.Program;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "generation")
public class Generation extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @OneToOne(mappedBy = "generation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Program program;

    public Generation(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public void update(String name, int year) {
        this.name = name;
        this.year = year;
    }
}