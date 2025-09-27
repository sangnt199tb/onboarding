package com.example.onboarding.presentation.service;

import com.example.onboarding.presentation.model.SdkConfigModel;
import com.example.onboarding.presentation.model.UploadFileResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface SdkConfigService {
    boolean saveSdk(SdkConfigModel sdkConfigModel);

    List<SdkConfigModel> findAllSdk();

    void testAsync();

    boolean checkStatusSdk(String sdkCode, HttpServletRequest httpServletRequest);

    List<String> getAllListSdkByStatus(String status, HttpServletRequest httpServletRequest);
}
