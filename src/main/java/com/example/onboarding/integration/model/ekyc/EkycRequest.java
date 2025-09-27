package com.example.onboarding.integration.model.ekyc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EkycRequest {
    private String id;
    private String faceA;
    private String faceB;
    private String faceC;
    private String faceD;
}
