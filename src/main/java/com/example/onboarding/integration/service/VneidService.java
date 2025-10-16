package com.example.onboarding.integration.service;

import com.example.onboarding.integration.model.vneid.CallBackVneidRequest;
import com.example.onboarding.integration.model.vneid.CallBackVneidResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VneidService {
    CallBackVneidResponse callBackVneid(CallBackVneidRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
