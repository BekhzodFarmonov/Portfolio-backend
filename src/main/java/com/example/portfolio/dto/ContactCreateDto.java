package com.example.portfolio.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ContactCreateDto {
@NotBlank
private String name;
@Email
private String email;
@NotBlank
private String message;
}