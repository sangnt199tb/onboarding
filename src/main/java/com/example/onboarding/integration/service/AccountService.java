package com.example.onboarding.integration.service;

import com.example.onboarding.integration.model.accountIntegration.AccountInterResponse;
import com.example.onboarding.integration.model.accountIntegration.TransactionResponse;

public interface AccountService {
    AccountInterResponse getListAccountByUserId(String userId);

    TransactionResponse getListTransactionByAccount(String accountId);

    TransactionResponse getListTransactionByAccountTwo(String accountId);
}
