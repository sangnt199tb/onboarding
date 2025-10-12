package com.example.onboarding.presentation.service;

import com.example.onboarding.presentation.model.DownloadFileRequest;
import com.example.onboarding.presentation.model.ExportFileRequest;
import com.example.onboarding.presentation.model.ExportFileResponse;
import com.example.onboarding.presentation.model.UploadFileResponse;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface FileService {
    UploadFileResponse uploadFile(MultipartFile file, String phoneNumber, String fileType, HttpServletRequest httpServletRequest) throws IOException;

    ResponseEntity<byte[]> downloadFile(DownloadFileRequest downloadFileRequest) throws IOException;

    boolean deleteFileById(String fileId) throws IOException;

    ExportFileResponse exportFile(ExportFileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, WriterException;
}
