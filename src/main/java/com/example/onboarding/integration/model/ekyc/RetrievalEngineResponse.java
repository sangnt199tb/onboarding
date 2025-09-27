package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class RetrievalEngineResponse {
    private String name;
    private String version;
    private Integer latency_ms;
    private String search_algorithm;
    private Integer top_k;
}
