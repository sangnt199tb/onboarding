package com.example.onboarding.integration.model.accountIntegration;

import lombok.Data;

import java.util.List;

@Data
public class AccountInterResponse {
    private String accountId;
    private List<AccountInfoIntegration> accounts;
}
