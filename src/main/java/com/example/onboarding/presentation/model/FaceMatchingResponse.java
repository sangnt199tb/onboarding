package com.example.onboarding.presentation.model;

import lombok.Data;

@Data
public class FaceMatchingResponse {
    private String resultFaceMatching;
    private String resultLiveness;
    private String resultFaceRetrival;
}
