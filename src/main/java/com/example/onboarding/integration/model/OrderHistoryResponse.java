package com.example.onboarding.integration.model;

import lombok.Data;

@Data
public class OrderHistoryResponse {
    private String timestamp;
    private String status;
    private String note;
}
