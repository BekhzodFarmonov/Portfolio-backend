package com.example.portfolio.service;


import com.example.portfolio.domain.MediaFile;
import com.example.portfolio.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MediaService {
private final MediaRepository repository;


@Value("${app.media.storage}")
private String storage;


@Value("${app.media.local-path}")
private String localPath;


public MediaFile upload(MultipartFile file) throws IOException {
Path root = Paths.get(localPath);
Files.createDirectories(root);
String ext = FilenameUtils.getExtension(file.getOriginalFilename());
String filename = UUID.randomUUID() + (ext != null && !ext.isEmpty() ? "." + ext : "");
Path target = root.resolve(filename);
file.transferTo(target);
MediaFile mf = MediaFile.builder()
.originalName(file.getOriginalFilename())
.url("/uploads/" + filename)
.contentType(file.getContentType())
.size(file.getSize())
.build();
return repository.save(mf);
}
}