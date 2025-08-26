package com.example.portfolio.domain.base;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.Instant;
import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@CreationTimestamp
@Column(updatable = false)
private Instant createdAt;


@UpdateTimestamp
private Instant updatedAt;
}