package com.example.onboarding.integration.model.kyc;

import lombok.Data;

@Data
public class LivenessCheckKycResponse {
    private boolean success;
    private float liveness_score;
    private float threshold;
    private boolean is_live;
    private String message;
}
