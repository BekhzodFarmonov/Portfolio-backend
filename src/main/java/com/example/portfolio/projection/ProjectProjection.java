package com.example.portfolio.projection;

import java.util.UUID;


public interface ProjectProjection {
   Long getId();
   String getTitle();
   String getShortDescription();
   String getCoverImageUrl();
   boolean isPublished();
}