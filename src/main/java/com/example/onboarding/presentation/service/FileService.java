package com.example.onboarding.presentation.service;

import com.example.onboarding.presentation.model.*;
import com.google.zxing.WriterException;
import io.minio.errors.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileService {
    UploadFileResponse uploadFile(MultipartFile file, String phoneNumber, String fileType, String bucketType, HttpServletRequest httpServletRequest) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    ResponseEntity<byte[]> downloadFile(DownloadFileRequest downloadFileRequest) throws IOException;

    boolean deleteFileById(String fileId) throws IOException;

    ExportFileResponse exportFile(ExportFileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, WriterException;

    GenFileContractResponse exportFileBaseString(GenFileContractRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;

    ResponseEntity<byte[]> downloadFileMinio(DownloadFileRequest downloadFileRequest);
}
