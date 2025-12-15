package com.yzpocket.esg_showcase_app.mentor.domain;

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
@Table(name = "mentor")
public class Mentor extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mentorname;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Production> productions = new ArrayList<>();

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    public Mentor(String mentorname, String description) {
        this.mentorname = mentorname;
        this.description = description;
    }

    public void update(String mentorname, String description) {
        this.mentorname = mentorname;
        this.description = description;
    }
}