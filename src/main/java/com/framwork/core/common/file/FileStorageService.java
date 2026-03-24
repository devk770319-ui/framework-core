package com.framwork.core.common.file;

import com.framwork.core.common.exception.BizException;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path baseDir;
    private final Map<String, StoredFile> metadataStore = new ConcurrentHashMap<>();

    public FileStorageService(StorageProperties properties) {
        this.baseDir = Paths.get(properties.getBaseDir()).toAbsolutePath().normalize();
    }

    @PostConstruct
    void init() {
        try {
            Files.createDirectories(baseDir);
        } catch (IOException ex) {
            throw new BizException("FILE-INIT-001", ex);
        }
    }

    public StoredFile save(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BizException("FILE-400");
        }

        String originalName = StringUtils.cleanPath(file.getOriginalFilename() == null ? "unknown" : file.getOriginalFilename());
        String fileId = UUID.randomUUID().toString();
        Path targetPath = baseDir.resolve(fileId + "_" + originalName).normalize();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new BizException("FILE-500", ex);
        }

        StoredFile storedFile = new StoredFile(fileId, originalName, targetPath.toString(), file.getSize(), file.getContentType());
        metadataStore.put(fileId, storedFile);
        return storedFile;
    }

    public StoredFile findById(String fileId) {
        StoredFile storedFile = metadataStore.get(fileId);
        if (storedFile == null) {
            throw new BizException("FILE-404");
        }
        return storedFile;
    }

    public Resource loadAsResource(String fileId) {
        StoredFile storedFile = findById(fileId);
        try {
            Path filePath = Paths.get(storedFile.storedPath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new BizException("FILE-404");
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new BizException("FILE-500", ex);
        }
    }
}
