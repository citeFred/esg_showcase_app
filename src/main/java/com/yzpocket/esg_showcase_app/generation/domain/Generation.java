package com.yzpocket.esg_showcase_app.generation.domain;

import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.program.domain.Program;
import com.yzpocket.esg_showcase_app.team.domain.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "generation")
public class Generation extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String genname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @OneToMany(mappedBy = "generation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    public Generation(String genname, Program program) {
        this.genname = genname;
        this.program = program;
    }

    public void update(String genname) {
        this.genname = genname;
    }
}