package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.persistence.domain.ManageFileEntity;
import com.example.onboarding.persistence.repository.ManageFileRepo;
import com.example.onboarding.presentation.configuration.AppConfiguration;
import com.example.onboarding.presentation.exception.ErrorCode;
import com.example.onboarding.presentation.exception.OnboardingException;
import com.example.onboarding.presentation.model.DownloadFileRequest;
import com.example.onboarding.presentation.model.ExportFileRequest;
import com.example.onboarding.presentation.model.ExportFileResponse;
import com.example.onboarding.presentation.model.UploadFileResponse;
import com.example.onboarding.presentation.service.FileService;
import com.example.onboarding.presentation.util.VelocityUtils;
import com.example.onboarding.presentation.validator.Validate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class FileServiceImpl implements FileService {
    private final AppConfiguration appConfiguration;
    private final ManageFileRepo manageFileRepo;
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @jakarta.annotation.Resource
    private TemplateEngine templateEngine;

    @jakarta.annotation.Resource
    private VelocityUtils velocityUtils;

    @Autowired
    public FileServiceImpl(AppConfiguration appConfiguration, ManageFileRepo manageFileRepo) {
        this.appConfiguration = appConfiguration;
        this.manageFileRepo = manageFileRepo;
    }


    @Override
    public UploadFileResponse uploadFile(MultipartFile file, String phoneNumber, String fileType, HttpServletRequest httpServletRequest) throws IOException {
        // validate file name
        Validate.validateFileName(file.getOriginalFilename());

        // validate file type
        Validate.validateFileType(file.getOriginalFilename());

        String fileName = genFileName(phoneNumber) + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        String filePath = appConfiguration.getSaveFile() + File.separator + fileName;
        file.transferTo(new File(filePath));

        UploadFileResponse response = new UploadFileResponse();

        //save manageFile
        ManageFileEntity manageFileEntity = new ManageFileEntity();
        manageFileEntity.setId(UUID.randomUUID().toString());
        manageFileEntity.setCreatedBy(phoneNumber);
        manageFileEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        manageFileEntity.setFileName(file.getOriginalFilename());
        manageFileEntity.setFilePath(filePath);
        manageFileEntity.setFileStatus("CREATE");
        manageFileEntity.setFileType(fileType);
        manageFileEntity.setFomart(file.getContentType());

        manageFileEntity = manageFileRepo.save(manageFileEntity);
        response.setFileId(manageFileEntity.getId());
        return response;
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(DownloadFileRequest downloadFileRequest) throws IOException {
        byte[] resource = null;
        ManageFileEntity entity = manageFileRepo.findFirstById(downloadFileRequest.getId());
        File file = new File(entity.getFilePath());
        if(file.exists()){
            resource = IOUtils.toByteArray(loadFIleAsResource(entity.getFilePath()).getInputStream());
            return ResponseEntity.ok()
                    .contentLength(resource.length)
                    .contentType(MediaType.parseMediaType(entity.getFomart()))
                    .body(resource);
        }
        return null;
    }

    @Override
    public boolean deleteFileById(String fileId) throws IOException {
        ManageFileEntity entity = manageFileRepo.findFirstById(fileId);
        try {
            if(Objects.nonNull(entity)){
                if(StringUtils.isNotBlank(entity.getFilePath())){
                    Path filePath = Paths.get(entity.getFilePath()).toAbsolutePath().normalize();
                    System.out.println("deleteFileById filePath: " + filePath.toString());
                    Resource resource = new UrlResource(filePath.toUri());
                    if(resource.exists()){
                        System.out.println("====deleteFileById filePath ton tai====");
                        Files.delete(Paths.get(entity.getFilePath()).toAbsolutePath().normalize());
                        entity.setFileStatus("DELETE");
                        manageFileRepo.save(entity);
                        return true;
                    } else {
                        throw new OnboardingException(ErrorCode.FILE_NOT_FOUND);
                    }
                } else {
                    return false;
                }
            } else {
                throw new OnboardingException(ErrorCode.FILE_NOT_FOUND);
            }
        } catch (Exception e){
            if(e instanceof TimeoutException){
                throw new OnboardingException(ErrorCode.TIME_OUT);
            }
            System.out.println("====deleteFileById with error detail: ====" + e);
            throw e;
        }
    }

    @Override
    public ExportFileResponse exportFile(ExportFileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        try {
            ExportFileResponse exportFileResponse = new ExportFileResponse();
            Map<String, Object> data = insertData(request.getId());
            Context context = new Context();
            context.setVariables(data);
            String fileName = "infoCus";
            String htmlContent = templateEngine.process(fileName, context);
            String miniType = "application/pdf;charset=UTF-8";
            httpServletResponse.setContentType(miniType);
            String uuid = UUID.randomUUID().toString();
            String nameFile = URLEncoder.encode("info customer_" + uuid +".pdf", "UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + nameFile);
            velocityUtils.genFilePdf(httpServletResponse, htmlContent);
            exportFileResponse.setMessage("SUCCESS");
            return exportFileResponse;
        } catch (Exception e){
            logger.error("FileServiceImpl exportFile with error detail: {}", e);
            throw e;
        }
    }

    private Map<String, Object> insertData(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountName","sang lang thang");
        map.put("accountNumber","231231231");
        return map;
    }

    public Resource loadFIleAsResource(String path){
        try {
            Path filePath;
            filePath = Paths.get(path).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
        throw new OnboardingException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private String getRandomString(){
        String alphabet = "zxcvbnmasdfghjklpoiuytrewq";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private String genFileName(String phoneNumber){
        return String.join("_", phoneNumber, String.valueOf(new Date().getTime()), getRandomString());
    }
}
