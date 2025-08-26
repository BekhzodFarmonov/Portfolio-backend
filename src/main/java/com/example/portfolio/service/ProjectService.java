package com.example.portfolio.service;


import com.example.portfolio.domain.Project;
import com.example.portfolio.dto.project.ProjectCreateDto;
import com.example.portfolio.dto.project.ProjectResponseDto;
import com.example.portfolio.dto.project.ProjectUpdateDto;
import com.example.portfolio.projection.ProjectProjection;
import com.example.portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;


    public Page<ProjectProjection> listPublic(Pageable pageable) {
        return repository.findByPublishedTrue(pageable);
    }


    public Page<ProjectProjection> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(p -> (ProjectProjection) new ProjectProjection() {
            @Override public Long getId() { return p.getId(); }
            @Override public String getTitle() { return p.getTitle(); }
            @Override public String getShortDescription() { return p.getShortDescription(); }
            @Override public String getCoverImageUrl() { return p.getCoverImageUrl(); }
            @Override public boolean isPublished() { return p.isPublished(); }
        });
    }

    public ProjectResponseDto create(ProjectCreateDto dto) {
        Project p = Project.builder()
                .title(dto.getTitle())
                .shortDescription(dto.getShortDescription())
                .description(dto.getDescription())
                .coverImageUrl(dto.getCoverImageUrl())
                .githubUrl(dto.getGithubUrl())
                .demoUrl(dto.getDemoUrl())
                .published(dto.isPublished())
                .build();
        repository.save(p);
        return toResponse(p);
    }


    public ProjectResponseDto update(UUID id, ProjectUpdateDto dto) {
        Project p = repository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        p.setTitle(dto.getTitle());
        p.setShortDescription(dto.getShortDescription());
        p.setDescription(dto.getDescription());
        p.setCoverImageUrl(dto.getCoverImageUrl());
        p.setGithubUrl(dto.getGithubUrl());
        p.setDemoUrl(dto.getDemoUrl());
        p.setPublished(dto.isPublished());
        repository.save(p);
        return toResponse(p);
    }


    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public ProjectResponseDto get(UUID id) {
        Project p = repository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        return toResponse(p);
    }

    private ProjectResponseDto toResponse(Project p) {
        ProjectResponseDto r = new ProjectResponseDto();
        r.setId(p.getId());
        r.setTitle(p.getTitle());
        r.setShortDescription(p.getShortDescription());
        r.setDescription(p.getDescription());
        r.setCoverImageUrl(p.getCoverImageUrl());
        r.setGithubUrl(p.getGithubUrl());
        r.setDemoUrl(p.getDemoUrl());
        r.setPublished(p.isPublished());
        return r;
    }
}