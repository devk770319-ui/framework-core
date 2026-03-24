package com.framwork.core.common.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/files")
@Tag(name = "File API")
public class FileController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "Upload file")
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StoredFile upload(@RequestPart("file") MultipartFile file) {
        return fileStorageService.save(file);
    }

    @Operation(summary = "Download file by fileId")
    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> download(@PathVariable String fileId) {
        StoredFile storedFile = fileStorageService.findById(fileId);
        Resource resource = fileStorageService.loadAsResource(fileId);
        String encodedFilename = URLEncoder.encode(storedFile.originalFilename(), StandardCharsets.UTF_8);

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(encodedFilename, StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
