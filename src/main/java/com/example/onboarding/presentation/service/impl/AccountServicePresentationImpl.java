package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.integration.model.accountIntegration.AccountInterResponse;
import com.example.onboarding.integration.model.accountIntegration.TransactionResponse;
import com.example.onboarding.integration.service.AccountService;
import com.example.onboarding.presentation.model.OnboardTransModel;
import com.example.onboarding.presentation.model.account.AccountInfo;
import com.example.onboarding.presentation.model.account.AccountRequestBody;
import com.example.onboarding.presentation.model.account.AccountResponseBody;
import com.example.onboarding.presentation.model.account.TransactionInfo;
import com.example.onboarding.presentation.service.AccountServicePresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class AccountServicePresentationImpl implements AccountServicePresentation {

    @Autowired
    @Qualifier("taskExecutor")
    private Executor taskExecutor;

    private final AccountService accountService;

    @Autowired
    public AccountServicePresentationImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountResponseBody getListTransactionByUserId(AccountRequestBody accountRequestBody) {

        AccountInterResponse accountInterResponse = getListAccountByUserId(accountRequestBody.getCifNumber());
        AccountResponseBody accountResponseBody = new AccountResponseBody();
        List<AccountInfo> accountInfos = new ArrayList<>();

        return null;
    }

    private AccountInterResponse getListAccountByUserId(String userId){
        return accountService.getListAccountByUserId(userId);
    }
}
