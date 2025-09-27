package com.example.onboarding.integration.service.impl;

import com.example.onboarding.integration.configuration.CallOnlineConfiguration;
import com.example.onboarding.integration.model.accountIntegration.AccountInterRequest;
import com.example.onboarding.integration.model.accountIntegration.AccountInterResponse;
import com.example.onboarding.integration.model.accountIntegration.TransactionRequest;
import com.example.onboarding.integration.model.accountIntegration.TransactionResponse;
import com.example.onboarding.integration.service.AccountService;
import com.example.onboarding.presentation.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {

    private final RestTemplate restTemplate;
    private final CallOnlineConfiguration callOnlineConfiguration;

    @Autowired
    public AccountServiceImpl(RestTemplate restTemplate, CallOnlineConfiguration callOnlineConfiguration) {
        this.restTemplate = restTemplate;
        this.callOnlineConfiguration = callOnlineConfiguration;
    }

    @Override
    public AccountInterResponse getListAccountByUserId(String userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AccountInterRequest accountInterRequest = AccountInterRequest
                .builder()
                .userId(userId)
                .build();

        HttpEntity<AccountInterRequest> httpEntity = new HttpEntity<>(accountInterRequest, headers);
        ResponseEntity<AccountInterResponse> response = restTemplate.postForEntity(
                callOnlineConfiguration.getGetListAccountByUserId(), httpEntity, AccountInterResponse.class);

        return response.getBody();
    }

    @Override
    public TransactionResponse getListTransactionByAccount(String accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransactionRequest transactionRequest = TransactionRequest.builder().accountId(accountId).build();
        HttpEntity<TransactionRequest> httpEntity = new HttpEntity<>(transactionRequest, headers);

        ResponseEntity<TransactionResponse> response = restTemplate.postForEntity(
                callOnlineConfiguration.getGetListTransactionByAccountOne(), httpEntity, TransactionResponse.class
        );

        return response.getBody();
    }

    @Override
    public TransactionResponse getListTransactionByAccountTwo(String accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransactionRequest transactionRequest = TransactionRequest.builder().accountId(accountId).build();
        HttpEntity<TransactionRequest> httpEntity = new HttpEntity<>(transactionRequest, headers);

        ResponseEntity<TransactionResponse> response = restTemplate.postForEntity(
                callOnlineConfiguration.getGetListTransactionByAccountTwo(), httpEntity, TransactionResponse.class
        );

        return response.getBody();
    }
}
