package com.framwork.core.common.file;

public record StoredFile(
        String fileId,
        String originalFilename,
        String storedPath,
        long size,
        String contentType
) {
}
