package com.example.onboarding.integration.model.kyc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaceMatchingRequest {
    private String id;
    private String faceA;
    private String faceB;
    private String faceC;
    private String faceD;
}
