package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class EkycResultResponse {
    private InfoFaceMatchingResponse face_matching;
    private InfoLivenessCheckResponse liveness_check;
    private InfoFaceRetrievalResponse face_retrieval;
}
