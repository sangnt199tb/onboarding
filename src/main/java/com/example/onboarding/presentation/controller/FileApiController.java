package com.example.onboarding.presentation.controller;

import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.service.FileService;
import com.example.onboarding.presentation.service.impl.MinioServiceImpl;
import com.google.zxing.WriterException;
import io.minio.errors.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/v1/file")
@RestController
public class FileApiController {

    private final FileService fileService;
    private final MinioServiceImpl minioService;


    @Autowired
    public FileApiController(FileService fileService, MinioServiceImpl minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public UploadFileResponse uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("fileType") String fileType,
            @RequestParam("bucketType") String bucketType,
            HttpServletRequest httpServletRequest) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return fileService.uploadFile(file, phoneNumber, fileType, bucketType,httpServletRequest);
    }

    @PostMapping("/download-file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadFile(@RequestBody DownloadFileRequest downloadFileRequest) throws IOException {
        return fileService.downloadFile(downloadFileRequest);
    }

    @GetMapping("/delete-file/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteFile(@PathVariable("fileId") String fileId) throws IOException {
        return fileService.deleteFileById(fileId);
    }

    @PostMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    public ExportFileResponse exportFile(@RequestBody ExportFileRequest request,
                                         HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, WriterException {
        return fileService.exportFile(request, httpServletRequest, httpServletResponse);
    }

    @PostMapping("/export/base")
    @ResponseStatus(HttpStatus.OK)
    public GenFileContractResponse exportFileBaseString(@RequestBody GenFileContractRequest request,
                                              HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, WriterException {
        return fileService.exportFileBaseString(request, httpServletRequest, httpServletResponse);
    }

    @PostMapping("/upload-minio")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String objectName = minioService.uploadFile(file, null);
        return ResponseEntity.ok(objectName);
    }

    @PostMapping("/download-file-minio")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadFileMinio(@RequestBody DownloadFileRequest downloadFileRequest) throws IOException {
        return fileService.downloadFileMinio(downloadFileRequest);
    }
}
