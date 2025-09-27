package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class MetaFaceRetrievalResponse {
    private String location;
    private String source;
    private String last_verified;
}
