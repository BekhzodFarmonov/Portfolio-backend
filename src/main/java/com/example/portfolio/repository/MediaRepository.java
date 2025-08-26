package com.example.portfolio.repository;


import com.example.portfolio.domain.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface MediaRepository extends JpaRepository<MediaFile, UUID> {}