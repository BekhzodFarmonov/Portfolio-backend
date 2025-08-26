package com.example.portfolio.controller;



import com.example.portfolio.domain.ContactMessage;
import com.example.portfolio.dto.ApiResponse;
import com.example.portfolio.dto.ContactCreateDto;
import com.example.portfolio.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/contact")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class ContactController {
private final ContactRepository repository;


@PostMapping
public ApiResponse<Void> send(@Valid @RequestBody ContactCreateDto dto) {
    log.info("Received request to send contact with dto: {}", dto);
repository.save(ContactMessage.builder().name(dto.getName()).email(dto.getEmail()).message(dto.getMessage()).build());
return ApiResponse.ok("Received", null);
}


@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public ApiResponse<?> list() {
    log.info("Received request to list all contacts");
    return ApiResponse.ok(repository.findAll()); }
}