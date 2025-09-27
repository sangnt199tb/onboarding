package com.example.onboarding.integration.model.accountIntegration;

import lombok.Data;

@Data
public class TransactionAccountDetail {
    private String transactionId;
    private double amount;
    private String date;
}
