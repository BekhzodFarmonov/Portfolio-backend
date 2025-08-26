package com.example.portfolio.controller;


import com.example.portfolio.dto.*;
import com.example.portfolio.projection.BlogProjection;
import com.example.portfolio.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService service;

    @GetMapping("/public")
    public ApiResponse<PageResponse<BlogProjection>> listPublic(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size
    ) {
        log.info("📥 Public bloglar ro‘yxati so‘raldi: page={}, size={}", page, size);
        Page<BlogProjection> p = service.listPublic(PageRequest.of(page, size));
        log.info("✅ {} ta public blog qaytarildi", p.getContent().size());
        return ApiResponse.ok(PageResponse.from(p));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PageResponse<BlogProjection>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        log.info("📥 Admin tomonidan barcha bloglar so‘raldi: page={}, size={}", page, size);
        Page<BlogProjection> p = service.listAll(PageRequest.of(page, size));
        log.info("✅ {} ta blog qaytarildi (admin view)", p.getContent().size());
        return ApiResponse.ok(PageResponse.from(p));
    }

    @GetMapping("/{id}")
    public ApiResponse<BlogResponseDto> get(@PathVariable UUID id) {
        log.info("📥 Blog ID orqali so‘raldi: {}", id);
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<BlogResponseDto> getBySlug(@PathVariable String slug) {
        log.info("📥 Blog slug orqali so‘raldi: {}", slug);
        return ApiResponse.ok(service.getBySlug(slug));

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BlogResponseDto> create(@Valid @RequestBody BlogCreateDto dto) {
        log.info("📝 Yangi blog yaratish: title='{}', slug='{}'", dto.getTitle(), dto.getSlug());
        return ApiResponse.ok("Created", service.create(dto));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BlogResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody BlogUpdateDto dto
    ) {
        log.info("✏️ Blog yangilash: id={}, title='{}'", id, dto.getTitle());
        return ApiResponse.ok("Updated", service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        log.warn("🗑 Blog o‘chirish so‘raldi: id={}", id);
        service.delete(id);
        return ApiResponse.ok("Deleted", null);
    }
}
