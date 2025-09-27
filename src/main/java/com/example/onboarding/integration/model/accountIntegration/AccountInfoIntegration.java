package com.example.onboarding.integration.model.accountIntegration;

import lombok.Data;

@Data
public class AccountInfoIntegration {
    private String accountId;
    private double balance;
    private String status;
    private String createdAt;
}
