package com.example.portfolio.repository;


import com.example.portfolio.domain.Blog;
import com.example.portfolio.projection.BlogProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface BlogRepository extends JpaRepository<Blog, UUID> {
Page<BlogProjection> findByPublishedTrue(Pageable pageable);

Optional<Blog> findBySlugAndPublishedTrue(String slug);
}