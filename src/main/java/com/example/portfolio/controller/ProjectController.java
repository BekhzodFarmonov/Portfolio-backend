package com.example.portfolio.controller;

import com.example.portfolio.dto.ApiResponse;
import com.example.portfolio.dto.PageResponse;
import com.example.portfolio.dto.project.ProjectCreateDto;
import com.example.portfolio.dto.project.ProjectResponseDto;
import com.example.portfolio.dto.project.ProjectUpdateDto;
import com.example.portfolio.projection.ProjectProjection;
import com.example.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping("/public")
    public ApiResponse<PageResponse<ProjectProjection>> listPublic(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size
    ) {
        Page<ProjectProjection> p = service.listPublic(PageRequest.of(page, size));
        log.info("Received request to list all projects");
        return ApiResponse.ok(PageResponse.from(p));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PageResponse<ProjectProjection>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Page<ProjectProjection> p = service.listAll(PageRequest.of(page, size));
        log.info("Received request to list all  for Admin projects");
        return ApiResponse.ok(PageResponse.from(p));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProjectResponseDto> get(@PathVariable UUID id) {
        log.info("Received request to get project by id {}", id);
        return ApiResponse.ok(service.get(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProjectResponseDto> create(@Valid @RequestBody ProjectCreateDto dto) {
        log.info("Received request to create project {}", dto);
        return ApiResponse.ok("Created", service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProjectResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectUpdateDto dto
    ) {
        log.info("Received request to update project {}", dto);
        return ApiResponse.ok("Updated", service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        log.info("Received request to delete project {}", id);
        service.delete(id);
        log.info("Deleted project {}", id);
        return ApiResponse.ok("Deleted", null);
    }
}
