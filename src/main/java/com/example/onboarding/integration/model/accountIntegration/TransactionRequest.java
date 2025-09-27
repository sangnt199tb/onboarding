package com.example.onboarding.integration.model.accountIntegration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {
    private String accountId;
}
