package com.example.portfolio.controller;



import com.example.portfolio.dto.ApiResponse;
import com.example.portfolio.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/settings")

@RequiredArgsConstructor
public class SettingController {
private final SettingService service;


@GetMapping
public ApiResponse<String> get(@RequestParam String key) {
log.info("Received request to get setting {}", key);
    return ApiResponse.ok(service.get(key));
}


@PostMapping
@PreAuthorize("hasRole('ADMIN')")
public ApiResponse<String> upsert(@RequestParam String key, @RequestParam String value) {
    log.info("Received request to upsert setting {}", key);
return ApiResponse.ok("Saved", service.upsert(key, value).getValue());
}
}