package com.example.onboarding.integration.service.impl;

import com.example.onboarding.integration.model.vneid.CallBackVneidRequest;
import com.example.onboarding.integration.model.vneid.CallBackVneidResponse;
import com.example.onboarding.integration.service.VneidService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VneidServiceImpl implements VneidService {
    @Override
    @Async("onboardingAsync")
    public CallBackVneidResponse callBackVneid(CallBackVneidRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
