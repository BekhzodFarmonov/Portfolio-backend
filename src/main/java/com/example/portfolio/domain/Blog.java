package com.example.portfolio.domain;



import com.example.portfolio.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog extends BaseEntity {
@Column(nullable = false)
private String title;


@Column(unique = true)
private String slug;


@Column(length = 500)
private String excerpt;


@Lob
private String content;


private String coverImageUrl;


private boolean published = true;
}