package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

import java.util.List;

@Data
public class QueryFaceResponse {
    private String upload_time;
    private String source_ip;
    private String image_url;
    private List<Double> embedding;
    private Integer embedding_dim;
}
