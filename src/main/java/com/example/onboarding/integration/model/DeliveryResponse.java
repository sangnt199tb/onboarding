package com.example.onboarding.integration.model;

import lombok.Data;

@Data
public class DeliveryResponse {
    private String status;
    private String carrier;
    private String tracking_number;
    private String estimated_delivery;
}
