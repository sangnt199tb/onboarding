package com.example.onboarding.presentation.service;

import com.example.onboarding.presentation.model.DownloadFileRequest;
import com.example.onboarding.presentation.model.UploadFileResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    UploadFileResponse uploadFile(MultipartFile file, String phoneNumber, String fileType, HttpServletRequest httpServletRequest) throws IOException;

    ResponseEntity<byte[]> downloadFile(DownloadFileRequest downloadFileRequest) throws IOException;

    boolean deleteFileById(String fileId) throws IOException;
}
