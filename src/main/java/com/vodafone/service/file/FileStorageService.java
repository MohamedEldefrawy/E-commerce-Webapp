package com.vodafone.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void store(MultipartFile file);
}
