package com.yzpocket.esg_showcase_app.company.domain;

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
@Table(name = "company")
public class Company extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyname;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Production> productions = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    public Company(String companyname, String description) {
        this.companyname = companyname;
        this.description = description;
    }

    public void update(String companyname, String description) {
        this.companyname = companyname;
        this.description = description;
    }
}