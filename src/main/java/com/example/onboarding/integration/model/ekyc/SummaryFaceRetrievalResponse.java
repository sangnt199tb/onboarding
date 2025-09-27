package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

import java.util.List;

@Data
public class SummaryFaceRetrievalResponse {
    private Double highest_score;
    private Double lowest_score;
    private Double average_score;
    private List<String> matched_sources;
}
