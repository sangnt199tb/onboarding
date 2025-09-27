package com.example.onboarding.presentation.model;

import lombok.Data;

@Data
public class SdkConfigModel {
    private String id;
    private String partnerName;
    private String description;
    private String status;
    private String priority;
    private String requestLimit;
    private String requestQuantity;
}
