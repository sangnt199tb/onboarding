package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

import java.util.List;

@Data
public class ResultsFaceRetrievalResponse {
    private String status;
    private Integer match_count;
    private List<MatchesFaceRetrievalResponse> matches;
}
