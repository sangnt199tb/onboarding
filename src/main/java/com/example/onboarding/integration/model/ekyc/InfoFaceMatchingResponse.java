package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class InfoFaceMatchingResponse {
    private String status;
    private float score;
    private float threshold;
    private String message;
}
