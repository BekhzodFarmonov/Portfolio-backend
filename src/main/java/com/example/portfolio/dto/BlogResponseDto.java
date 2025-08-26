package com.example.portfolio.dto;

import lombok.Data;

import java.util.UUID;

@Data
 public class BlogResponseDto {
    private Long id;
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private String coverImageUrl;
    private boolean published;
}
