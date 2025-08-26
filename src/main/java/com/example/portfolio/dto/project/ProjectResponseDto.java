package com.example.portfolio.dto.project;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String shortDescription;
    private String description;
    private String coverImageUrl;
    private String githubUrl;
    private String demoUrl;
    private boolean published;
}
