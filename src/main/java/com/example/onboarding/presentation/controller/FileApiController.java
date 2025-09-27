package com.example.onboarding.presentation.controller;

import com.example.onboarding.presentation.model.DownloadFileRequest;
import com.example.onboarding.presentation.model.UploadFileResponse;
import com.example.onboarding.presentation.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/v1/file")
@RestController
public class FileApiController {

    private final FileService fileService;

    @Autowired
    public FileApiController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public UploadFileResponse uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("fileType") String fileType,
            HttpServletRequest httpServletRequest) throws IOException {
        return fileService.uploadFile(file, phoneNumber, fileType, httpServletRequest);
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

}
