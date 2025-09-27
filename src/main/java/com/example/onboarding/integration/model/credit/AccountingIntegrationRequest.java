package com.example.onboarding.integration.model.credit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountingIntegrationRequest {
    private String userId;
    private String token;
}
