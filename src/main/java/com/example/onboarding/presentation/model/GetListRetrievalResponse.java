package com.example.onboarding.presentation.model;

import lombok.Data;

import java.util.List;

@Data
public class GetListRetrievalResponse {
    private String status;
    private Integer matchCount;
    private List<RetrievalDetailResponse> retrievalDetailResponseList;
}
