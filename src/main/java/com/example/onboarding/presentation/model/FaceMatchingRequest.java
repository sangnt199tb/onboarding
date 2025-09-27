package com.example.onboarding.presentation.model;

import lombok.Data;

@Data
public class FaceMatchingRequest {
    private String id;
    private String faceA;
    private String faceB;
    private String faceC;
    private String faceD;
}
