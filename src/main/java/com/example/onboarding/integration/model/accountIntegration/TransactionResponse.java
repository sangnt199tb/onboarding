package com.example.onboarding.integration.model.accountIntegration;

import lombok.Data;

import java.util.List;

@Data
public class TransactionResponse {
    private String accountId;
    private List<TransactionAccountDetail> transactions;
}
