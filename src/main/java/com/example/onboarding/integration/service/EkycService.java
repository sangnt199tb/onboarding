package com.example.onboarding.integration.service;

import com.example.onboarding.integration.model.kyc.FaceMatchingResponse;
import com.example.onboarding.integration.model.kyc.FaceRetrievalKycResponse;
import com.example.onboarding.integration.model.kyc.LivenessCheckKycResponse;
import com.example.onboarding.presentation.model.FaceMatchingRequest;

public interface EkycService {
    FaceMatchingResponse compareFaceEkyc(FaceMatchingRequest request);

    LivenessCheckKycResponse livenessCheckEkyc (FaceMatchingRequest request);

    FaceRetrievalKycResponse faceRetrievalEkyc(FaceMatchingRequest request);
}
