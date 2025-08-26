package com.example.portfolio.domain;

import com.example.portfolio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 500)
    private String shortDescription;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String coverImageUrl;

    @Column(length = 500)
    private String githubUrl;

    @Column(length = 500)
    private String demoUrl;

    @Builder.Default
    private boolean published = true;
}
