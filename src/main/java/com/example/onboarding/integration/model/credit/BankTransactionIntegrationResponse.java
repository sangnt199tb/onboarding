package com.example.onboarding.integration.model.credit;

import lombok.Data;

import java.util.List;

@Data
public class BankTransactionIntegrationResponse {
    private List<String> recentTransactions;
}
