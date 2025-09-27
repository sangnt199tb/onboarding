package com.example.onboarding.presentation.model;

import lombok.Data;

@Data
public class RetrievalDetailResponse {
    private Double matchScore;
    private String personId;
    private String fullName;
    private String dob;
    private InfoCard infoCard;
    private String similarityMethod;
    private String matchedAt;
    private MetaDateRetrieval metaDateRetrieval;
}
