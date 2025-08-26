package com.example.portfolio.dto.project;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ProjectCreateDto {
@NotBlank
private String title;
private String shortDescription;
private String description;
private String coverImageUrl;
private String githubUrl;
private String demoUrl;
private boolean published = true;
}


