package com.example.onboarding.integration.service.impl;

import com.example.onboarding.integration.configuration.CallOnlineConfiguration;
import com.example.onboarding.integration.model.company.Company;
import com.example.onboarding.integration.model.company.CompanyRequest;
import com.example.onboarding.integration.service.CompanyServiceInter;
import com.example.onboarding.presentation.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyServiceInterImpl implements CompanyServiceInter {
    private final RestTemplate restTemplate;
    private final CallOnlineConfiguration callOnlineConfiguration;

    @Autowired
    public CompanyServiceInterImpl(RestTemplate restTemplate, CallOnlineConfiguration callOnlineConfiguration) {
        this.restTemplate = restTemplate;
        this.callOnlineConfiguration = callOnlineConfiguration;
    }


    @Override
    public Company getInfoCompanyInter(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CompanyRequest request = CompanyRequest.builder()
                .id(id)
                .build();

        HttpEntity<CompanyRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Company> response = restTemplate.postForEntity(
                callOnlineConfiguration.getInfoCompanyUrl(), httpEntity, Company.class);
        System.out.println("CompanyServiceInterImpl getInfoCompanyInter response: " + LogUtil.toJson(response));
        return response.getBody();
    }
}
