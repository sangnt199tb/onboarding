package com.example.onboarding.presentation.util;

import com.example.onboarding.presentation.service.impl.FileServiceImpl;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class VelocityUtils {
    private static final Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

    public void genFilePdf(HttpServletResponse response, String htmlContent) throws IOException {
        try(OutputStream os = response.getOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.useFastMode();
            builder.toStream(os);
            builder.run();
        } catch (IOException e){
            logger.error("VelocityUtils genFilePdf with error detail: {}", e);
            throw e;
        }
    }
}
