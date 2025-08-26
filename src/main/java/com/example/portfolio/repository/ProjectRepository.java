package com.example.portfolio.repository;


import com.example.portfolio.domain.Project;
import com.example.portfolio.projection.ProjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface ProjectRepository extends JpaRepository<Project, UUID> {

   Page<ProjectProjection> findByPublishedTrue(Pageable pageable);
}