package com.example.onboarding.presentation.controller;

import com.example.onboarding.presentation.model.SdkConfigModel;
import com.example.onboarding.presentation.model.UploadFileResponse;
import com.example.onboarding.presentation.service.SdkConfigService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/v1/sdk-config")
@RestController
public class SdkConfigController {

    private final SdkConfigService sdkConfigService;

    @Autowired
    public SdkConfigController(SdkConfigService sdkConfigService) {
        this.sdkConfigService = sdkConfigService;
    }

    @RequestMapping("/save-info")
    @ResponseStatus(HttpStatus.OK)
    public boolean saveSdkConfig(@RequestBody SdkConfigModel sdkConfigModel){
        return sdkConfigService.saveSdk(sdkConfigModel);
    }

    @GetMapping("/get-all-sdk")
    @ResponseStatus(HttpStatus.OK)
    public List<SdkConfigModel> findAffSdkConfig(){
        return sdkConfigService.findAllSdk();
    }

    @PostMapping("/check-status-sdk")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkStatusSdk(
            @RequestParam("sdkCode") String sdkCode,
            HttpServletRequest httpServletRequest) throws IOException {
        return sdkConfigService.checkStatusSdk(sdkCode, httpServletRequest);
    }

    @PostMapping("/get-all-list-sdk-by-status")
    public List<String> getAllListSdkByStatus(
            @RequestParam("status") String status, HttpServletRequest httpServletRequest){
        return sdkConfigService.getAllListSdkByStatus(status, httpServletRequest);
    }

    @GetMapping("/test-async")
    @ResponseStatus(HttpStatus.OK)
    public boolean testAsync(){
        System.out.println("====Start SdkConfigController testAsync====");
        sdkConfigService.testAsync();
        System.out.println("====Start SdkConfigController testAsync====");
        return true;
    }

}
