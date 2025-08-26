package com.example.portfolio.controller;



import com.example.portfolio.domain.MediaFile;
import com.example.portfolio.dto.ApiResponse;
import com.example.portfolio.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {
private final MediaService service;


@PostMapping(value = "/upload", consumes = {"multipart/form-data"})
@PreAuthorize("hasRole('ADMIN')")
public ApiResponse<MediaFile> upload(@RequestPart("file") MultipartFile file) throws Exception {
    log.info("Received request to upload contact information");
return ApiResponse.ok("Uploaded", service.upload(file));
}
}