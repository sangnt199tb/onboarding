package com.example.onboarding.integration.model.kyc;

import lombok.Data;

@Data
public class FaceRetrievalKycDetail {
    private String face_id;
    private float match_score;
    private String user_id;
    private MetaDataFaceRetrievalKyc metadata;
}
