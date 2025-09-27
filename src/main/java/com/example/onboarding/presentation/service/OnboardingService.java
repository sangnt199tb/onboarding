package com.example.onboarding.presentation.service;

import com.example.onboarding.presentation.model.FaceMatchingRequest;
import com.example.onboarding.presentation.model.FaceMatchingResponse;
import com.example.onboarding.presentation.model.OnboardTransModel;

public interface OnboardingService {
    String checkPhoneAndEmail(String phoneNumber, String email);

    OnboardTransModel findTransactionByPhone(String phoneNumber);

    FaceMatchingResponse compareFaceThread(FaceMatchingRequest request);
}
