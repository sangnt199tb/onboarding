package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class InfoLivenessCheckResponse {
    private String status;
    private String method;
    private float confidence;
    private String message;
}
