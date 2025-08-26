package com.example.portfolio.domain;



import com.example.portfolio.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "media_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFile extends BaseEntity {
private String originalName;
private String url;
private String contentType;
private long size;
}