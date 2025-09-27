package com.example.onboarding.integration.service;

import com.example.onboarding.integration.model.credit.AccountingIntegrationResponse;
import com.example.onboarding.integration.model.credit.BankTransactionIntegrationResponse;
import com.example.onboarding.integration.model.credit.CreditCardIntegrationResponse;
import com.example.onboarding.integration.model.credit.CustomerIntegrationResponse;
import com.example.onboarding.presentation.model.multithreadedtest.AccountingRequest;
import com.example.onboarding.presentation.model.multithreadedtest.BankTransactionRequest;
import com.example.onboarding.presentation.model.multithreadedtest.CreditCardRequest;
import com.example.onboarding.presentation.model.multithreadedtest.CustomerRequest;

public interface CreditOnlineService {
    AccountingIntegrationResponse getAccount(AccountingRequest request);

    BankTransactionIntegrationResponse getBankTransaction(BankTransactionRequest request);

    CreditCardIntegrationResponse getCreditCard(CreditCardRequest request);

    CustomerIntegrationResponse getCustomer(CustomerRequest request);
}
