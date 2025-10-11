package com.yzpocket.esg_showcase_app.exhibition.domain;

import com.yzpocket.esg_showcase_app.common.domain.TimeStamped;
import com.yzpocket.esg_showcase_app.generation.domain.Generation;
import com.yzpocket.esg_showcase_app.production.domain.Production;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "exhibition")
@Entity
public class Exhibition extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExhibitionStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id", unique = true, nullable = false)
    private Generation generation;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Production> productions =  new ArrayList<>();

    public Exhibition(String title, String description, Generation generation) {
        this.title = title;
        this.description = description;
        this.generation = generation;
        this.status = ExhibitionStatus.PREPARING;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}