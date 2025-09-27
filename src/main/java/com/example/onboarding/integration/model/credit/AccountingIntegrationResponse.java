package com.example.onboarding.integration.model.credit;

import lombok.Data;

@Data
public class AccountingIntegrationResponse {
    private String totalDebt;
    private String latestInvoice;
}
