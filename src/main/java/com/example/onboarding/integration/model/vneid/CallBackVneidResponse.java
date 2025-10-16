package com.example.onboarding.integration.model.vneid;

import lombok.Data;

@Data
public class CallBackVneidResponse {
    private String uuid;
    private String status;
    private String message;
    private String time;
}
