package com.example.onboarding.presentation.service;

import com.example.onboarding.presentation.model.OnboardTransModel;
import com.example.onboarding.presentation.model.account.AccountRequestBody;
import com.example.onboarding.presentation.model.account.AccountResponseBody;

public interface AccountServicePresentation {
    AccountResponseBody getListTransactionByUserId(AccountRequestBody accountRequestBody);
}
