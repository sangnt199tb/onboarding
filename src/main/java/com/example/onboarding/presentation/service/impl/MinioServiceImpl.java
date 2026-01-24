package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.presentation.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final String bucketName = "onboard";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(),
                                    file.getSize(),
                                    -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Upload file to MinIO failed", e);
        }
    }
}
