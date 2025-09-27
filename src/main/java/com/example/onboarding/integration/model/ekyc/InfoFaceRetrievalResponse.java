package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

import java.util.List;

@Data
public class InfoFaceRetrievalResponse {
    private String status;
    private List<TopMatchesRetrivalResponse> top_matches;
    private String vector_query_id;
    private String message;
}
