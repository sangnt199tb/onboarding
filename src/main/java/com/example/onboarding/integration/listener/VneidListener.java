package com.example.onboarding.integration.listener;

import com.example.onboarding.integration.configuration.RequestContext;
import com.example.onboarding.integration.model.vneid.CallBackVneidRequest;
import com.example.onboarding.integration.model.vneid.CallBackVneidResponse;
import com.example.onboarding.integration.service.VneidService;
import com.example.onboarding.presentation.util.CheckSumUtils;
import com.google.zxing.WriterException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/v1/vneid/listener")
@RestController
public class VneidListener {

    private static final Logger logger = LoggerFactory.getLogger(VneidListener.class);

    private final VneidService vneidService;

    @Resource
    private CheckSumUtils checkSumUtils;

    @Resource
    private RequestContext requestContext;

    @Autowired
    public VneidListener(VneidService vneidService) {
        this.vneidService = vneidService;
    }

    @PostMapping("/call-back")
    @ResponseStatus(HttpStatus.OK)
    public CallBackVneidResponse exportFileBaseString(@RequestBody CallBackVneidRequest request,
                                                      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, WriterException {
        logger.info("Start VneidListener exportFileBaseString with request: {}", request);
        // check sum
        checkSumUtils.validateCheckSumVneid(requestContext.getRequest());

        // xử lý async
        vneidService.callBackVneid(request, httpServletRequest, httpServletResponse);
        logger.info("End VneidListener exportFileBaseString with request: {}", request);

        CallBackVneidResponse response = new CallBackVneidResponse();
        response.setStatus("0");
        response.setMessage("SUCCESS");
        response.setTime(new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()));
        response.setUuid(response.getUuid());
        return response;
    }
}
