package com.example.portfolio.service;

import com.example.portfolio.domain.Blog;
import com.example.portfolio.dto.BlogCreateDto;
import com.example.portfolio.dto.BlogResponseDto;
import com.example.portfolio.dto.BlogUpdateDto;
import com.example.portfolio.projection.BlogProjection;
import com.example.portfolio.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BlogService {
private final BlogRepository repository;


public Page<BlogProjection> listPublic(Pageable pageable) { return repository.findByPublishedTrue(pageable); }


public Page<BlogProjection> listAll(Pageable pageable) {
return repository.findAll(pageable).map(b -> (BlogProjection) new BlogProjection() {
@Override public Long getId() { return b.getId(); }
@Override public String getTitle() { return b.getTitle(); }
@Override public String getSlug() { return b.getSlug(); }
@Override public String getExcerpt() { return b.getExcerpt(); }
@Override public String getCoverImageUrl() { return b.getCoverImageUrl(); }
@Override public boolean isPublished() { return b.isPublished(); }
});
}


public BlogResponseDto create(BlogCreateDto dto) {
Blog b = Blog.builder()
.title(dto.getTitle())
.slug(dto.getSlug())
.excerpt(dto.getExcerpt())
.content(dto.getContent())
.coverImageUrl(dto.getCoverImageUrl())
.published(dto.isPublished())
.build();
repository.save(b);
return toResponse(b);
}


public BlogResponseDto update(UUID id, BlogUpdateDto dto) {
Blog b = repository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
b.setTitle(dto.getTitle());
b.setSlug(dto.getSlug());
b.setExcerpt(dto.getExcerpt());
b.setContent(dto.getContent());
b.setCoverImageUrl(dto.getCoverImageUrl());
b.setPublished(dto.isPublished());
repository.save(b);
return toResponse(b);
}


public void delete(UUID id) { repository.deleteById(id); }


public BlogResponseDto get(UUID id) {
Blog b = repository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
return toResponse(b);
}


public BlogResponseDto getBySlug(String slug) {
Blog b = repository.findBySlugAndPublishedTrue(slug).orElseThrow(() -> new RuntimeException("Post not found"));
return toResponse(b);
}


private BlogResponseDto toResponse(Blog b) {
BlogResponseDto r = new BlogResponseDto();
r.setId(b.getId());
r.setTitle(b.getTitle());
r.setSlug(b.getSlug());
r.setExcerpt(b.getExcerpt());
r.setContent(b.getContent());
r.setCoverImageUrl(b.getCoverImageUrl());
r.setPublished(b.isPublished());
return r;
}
}