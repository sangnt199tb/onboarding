package com.example.onboarding.integration.model.kyc;

import lombok.Data;

@Data
public class FaceMatchingResponse {
    private boolean success;
    private float match_score;
    private float threshold;
    private boolean is_match;
    private String message;
}
