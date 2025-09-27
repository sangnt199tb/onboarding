package com.example.onboarding.presentation.model.account;

import lombok.Data;

@Data
public class TransactionInfo {
    private String transactionId;
    private double amount;
    private String date;
}
