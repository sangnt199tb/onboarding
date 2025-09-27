package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class EkycResponse {
    private String transaction_id;
    private String timestamp;
    private String user_id;
    private EkycResultResponse results;
    private String status;
}
