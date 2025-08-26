package com.example.portfolio.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class BlogCreateDto {
@NotBlank
private String title;
private String slug;
private String excerpt;
private String content;
private String coverImageUrl;
private boolean published = true;
}


