package com.example.onboarding.presentation.controller;

import com.example.onboarding.presentation.model.account.AccountRequestBody;
import com.example.onboarding.presentation.model.account.AccountResponseBody;
import com.example.onboarding.presentation.service.AccountServicePresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/account")
@RestController
public class AccountController {

    private final AccountServicePresentation accountServicePresentation;

    @Autowired
    public AccountController(AccountServicePresentation accountServicePresentation) {
        this.accountServicePresentation = accountServicePresentation;
    }

    @RequestMapping(method = RequestMethod.POST, value = "get-transaction-by-user")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseBody getListTransactionByUserId(@RequestBody AccountRequestBody accountRequestBody){
        return accountServicePresentation.getListTransactionByUserId(accountRequestBody);
    }

    @PostMapping("/test")
    public AccountResponseBody get(@RequestBody AccountRequestBody accountRequestBody){
        return accountServicePresentation.getListTransactionByUserId(accountRequestBody);
    }
}
