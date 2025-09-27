package com.example.onboarding.integration.model.kyc;

import lombok.Data;

import java.util.List;

@Data
public class FaceRetrievalKycResponse {
    private boolean success;
    private String query_face_id;
    private List<FaceRetrievalKycDetail> matched_faces;
    private Integer top_k;
    private String message;
}
