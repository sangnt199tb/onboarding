package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.persistence.domain.ManageFileEntity;
import com.example.onboarding.persistence.repository.ManageFileRepo;
import com.example.onboarding.presentation.configuration.AppConfiguration;
import com.example.onboarding.presentation.exception.ErrorCode;
import com.example.onboarding.presentation.exception.OnboardingException;
import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.service.FileService;
import com.example.onboarding.presentation.util.PDFUtils;
import com.example.onboarding.presentation.util.VelocityUtils;
import com.example.onboarding.presentation.validator.Validate;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
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

import javax.imageio.ImageIO;
import javax.print.Doc;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
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
    public ExportFileResponse exportFile(ExportFileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, WriterException {
        try {
            ExportFileResponse exportFileResponse = new ExportFileResponse();
            Map<String, Object> data = insertData(request.getId());
            Context context = new Context();
            context.setVariables(data);
            String fileName = "infoCus";
            String htmlContent = templateEngine.process(fileName, context);
            logger.info("exportFile htmlContent: {}", htmlContent);
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

    @Override
    public GenFileContractResponse exportFileBaseString(GenFileContractRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        try {
            genFileContract();
            String filePath = appConfiguration.getFolderSaveFile();
            String contract = "0387501612" + "_" + "034094000298" +"_sdk_mwg" + ".pdf";
            byte[] input_file = Files.readAllBytes(Paths.get(filePath + contract));
            byte[] encodedBytes = Base64.getEncoder().encode(input_file);
            String encodeString = new String(encodedBytes);
            byte[] decodeBytes = Base64.getDecoder().decode(encodeString.getBytes());
            String responseBase64 = Base64.getEncoder().encodeToString(decodeBytes);
            File contractFile = new File(filePath + contract);
            logger.info("FileServiceImpl exportFileBaseString path {}", contractFile.getPath());
            if(contractFile.delete()){
                logger.info("FileServiceImpl exportFileBaseString delete file success");
            } else {
                logger.info("FileServiceImpl exportFileBaseString delete file failed");
            }
            GenFileContractResponse response = new GenFileContractResponse();
            response.setBaseContract(responseBase64);
            return response;
        } catch (Exception e){
            logger.error("FileServiceImpl exportFileBaseString with error detail: {}", e);
            throw e;
        }
    }

    public Document genFileContract() throws IOException {
        try {
            FontFactory.register(getClass().getClassLoader().getResource("lib/arial.ttf").getPath(), "Arial");
            Font boldFont = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 7f, Font.BOLD);
            FontFactory.register(getClass().getClassLoader().getResource("lib/arial.ttf").getPath(), "Arial");
            Font font = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 7f, Font.NORMAL);
            Font fontI = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 6f, Font.ITALIC);
            FontFactory.register(getClass().getClassLoader().getResource("lib/wingding.ttf").getPath(), "Wingding");
            Font fontW = FontFactory.getFont("Wingding", BaseFont.IDENTITY_H, 7f, Font.NORMAL);
            char checked = '\u00FE';
            char unchecked = '\u00A8';
            String contract = "0387501612" + "_" + "034094000298" + "_sdk_mwg" + ".pdf";

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(appConfiguration.getFolderSaveFile() + contract));
            writer.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
            document.open();

            PdfPTable header = new PdfPTable(1);
            header.setWidthPercentage(100);

            URL imageUrl = getClass().getClassLoader().getResource("lib/Capture.PNG");
            if(imageUrl == null){
                throw new OnboardingException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            Image image = Image.getInstance(imageUrl);
            PdfPCell imageCell = new PdfPCell(image, true);
            header.addCell(imageCell);

            PdfPTable infoCus = new PdfPTable(2);
            infoCus.setWidthPercentage(100);
            infoCus.addCell(PDFUtils.createCellText(1, 2, "Thông tin cá nhân", boldFont, new int[]{}));
            infoCus.addCell(PDFUtils.createCellText(1, 2, "Thông tin chi tiết", fontI, new int[]{}));
            infoCus.addCell(PDFUtils.createCellText(1, 1, "Họ và tên", font, new int[]{}));
            infoCus.addCell(PDFUtils.createCellText(1, 1, "Nguyễn Trọng Sang", font, new int[]{}));
            infoCus.addCell(PDFUtils.createCellText(1, 1, String.valueOf(unchecked), fontW, new int[]{}));
            infoCus.addCell(PDFUtils.createCellText(1, 1, String.valueOf(checked), fontW, new int[]{}));
            document.add(header);
            document.add(infoCus);
            document.close();
            return document;
        } catch (Exception e){
            logger.warn("FileServiceImpl genFileContract with error detail: {}", e);
            throw e;
        }
    }

    private Map<String, Object> insertData(String id) throws IOException, WriterException {
//        byte[] imageBytes = Files.readAllBytes(Paths.get("D:\\Personal\\VB2\\SangLangThang.PNG"));
//        String qrBase64 = Base64.getEncoder().encodeToString(imageBytes);
        String content = "Nguyễn Trọng Sang \n 22/12/2994 \n 202490080";
        String qrBase64 = generateQRCodeBase64(content, 300,300);
        Map<String, Object> map = new HashMap<>();
        map.put("idStudent","202490080");
        map.put("fullName","Nguyễn Trọng Sang");
        map.put("dateOfbirth", "22/12/1994");
        map.put("qrBase64", qrBase64);
        return map;
    }

    public String generateQRCodeBase64(String content, int width, int height)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", outputStream);
        byte[] qrBytes = outputStream.toByteArray();

        return Base64.getEncoder().encodeToString(qrBytes);
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
