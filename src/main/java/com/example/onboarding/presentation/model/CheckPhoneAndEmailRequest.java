package com.example.onboarding.presentation.model;

import lombok.Data;

@Data
public class CheckPhoneAndEmailRequest {
    private String phoneNumber;
    public String email;
}
