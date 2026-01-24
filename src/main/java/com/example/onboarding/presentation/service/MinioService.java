package com.example.onboarding.presentation.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
     String uploadFile(MultipartFile file);
}
