package com.example.onboarding.integration.service.impl;

import com.example.onboarding.integration.configuration.CallOnlineConfiguration;
import com.example.onboarding.integration.model.credit.*;
import com.example.onboarding.integration.service.CreditOnlineService;
import com.example.onboarding.presentation.model.multithreadedtest.AccountingRequest;
import com.example.onboarding.presentation.model.multithreadedtest.BankTransactionRequest;
import com.example.onboarding.presentation.model.multithreadedtest.CreditCardRequest;
import com.example.onboarding.presentation.model.multithreadedtest.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreditOnlineServiceImpl implements CreditOnlineService {

    private final RestTemplate restTemplate;
    private final CallOnlineConfiguration configuration;

    @Autowired
    public CreditOnlineServiceImpl(RestTemplate restTemplate, CallOnlineConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
    }

    @Override
    public AccountingIntegrationResponse getAccount(AccountingRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            AccountingIntegrationRequest accountingIntegrationRequest = AccountingIntegrationRequest
                    .builder()
                    .userId(request.getUserId())
                    .token(request.getToken())
                    .build();

            HttpEntity<AccountingIntegrationRequest> httpEntity = new HttpEntity<>(accountingIntegrationRequest, headers);

            ResponseEntity<AccountingIntegrationResponse> response =
                    restTemplate.postForEntity(
                            configuration.getAccountUrl(),
                            httpEntity,
                            AccountingIntegrationResponse.class
                    );

            return response.getBody();

        } catch (Exception e){
            System.out.println("CreditOnlineServiceImpl getAccount with error detail: " + e);
            throw e;
        }
    }

    @Override
    public BankTransactionIntegrationResponse getBankTransaction(BankTransactionRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            BankTransactionIntegrationRequest bankTransactionIntegrationRequest
                    = BankTransactionIntegrationRequest
                    .builder()
                    .userId(request.getUserId())
                    .build();

            HttpEntity<BankTransactionIntegrationRequest> httpEntity = new HttpEntity<>(bankTransactionIntegrationRequest, headers);

            ResponseEntity<BankTransactionIntegrationResponse> response = restTemplate.postForEntity(
                    configuration.getTransactionUrl(),
                    httpEntity,
                    BankTransactionIntegrationResponse.class
            );

            return response.getBody();
        } catch (Exception e){
            System.out.println("CreditOnlineServiceImpl getBankTransaction with error detail: " + e);
            throw e;
        }
    }

    @Override
    public CreditCardIntegrationResponse getCreditCard(CreditCardRequest request) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            CreditCardIntegrationRequest creditCardIntegrationRequest
                    = CreditCardIntegrationRequest.builder()
                    .userId(request.getUserId())
                    .build();

            HttpEntity<CreditCardIntegrationRequest> httpEntity = new HttpEntity<>(creditCardIntegrationRequest, headers);

            ResponseEntity<CreditCardIntegrationResponse> response =
                    restTemplate.postForEntity(
                            configuration.getCreditUrl(),
                            httpEntity,
                            CreditCardIntegrationResponse.class
                    );

            return response.getBody();

        } catch (Exception e){
            System.out.println("CreditOnlineServiceImpl getCreditCard with error detail: " + e);
            throw e;
        }
    }

    @Override
    public CustomerIntegrationResponse getCustomer(CustomerRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            CustomerIntegrationRequest customerIntegrationRequest =
                    CustomerIntegrationRequest.builder()
                            .userId(request.getUserId())
                            .build();
            HttpEntity<CustomerIntegrationRequest> httpEntity = new HttpEntity<>(customerIntegrationRequest, headers);

            ResponseEntity<CustomerIntegrationResponse> response =
                    restTemplate.postForEntity(
                            configuration.getCustomerUrl(),
                            httpEntity,
                            CustomerIntegrationResponse.class
                    );

            return response.getBody();

        } catch (Exception e){
            System.out.println("CreditOnlineServiceImpl getCustomer with error detail: " + e);
            throw e;
        }
    }
}
