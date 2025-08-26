package com.example.portfolio.projection;


import java.util.UUID;


public interface BlogProjection {
Long getId();
String getTitle();
String getSlug();
String getExcerpt();
String getCoverImageUrl();
boolean isPublished();
}