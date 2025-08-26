package com.example.portfolio.domain;



import com.example.portfolio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting extends BaseEntity {
@Column(unique = true, nullable = false)
private String keyName;


@Column(length = 2000)
private String value;
}