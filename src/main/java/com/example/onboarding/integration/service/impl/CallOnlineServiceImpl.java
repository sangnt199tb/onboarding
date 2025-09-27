package com.example.onboarding.integration.service.impl;

import com.example.onboarding.integration.configuration.CallOnlineConfiguration;
import com.example.onboarding.integration.model.CallOnlineRequest;
import com.example.onboarding.integration.model.CallOnlineResponse;
import com.example.onboarding.integration.model.CommerceRequest;
import com.example.onboarding.integration.model.CommerceResponse;
import com.example.onboarding.integration.model.ekyc.EkycRequest;
import com.example.onboarding.integration.model.ekyc.EkycResponse;
import com.example.onboarding.integration.model.ekyc.FaceRetrievalRequest;
import com.example.onboarding.integration.model.ekyc.FaceRetrievalResponse;
import com.example.onboarding.integration.service.CallOnlineService;
import com.example.onboarding.presentation.model.CommercePresentationRequest;
import com.example.onboarding.presentation.model.FaceMatchingRequest;
import com.example.onboarding.presentation.model.GetListRetrievalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@Service
public class CallOnlineServiceImpl implements CallOnlineService {

    private final RestTemplate restTemplate;
    private final CallOnlineConfiguration configuration;

    @Autowired
    public CallOnlineServiceImpl(RestTemplate restTemplate, CallOnlineConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
    }

    @Override
    public CallOnlineResponse testPostCallOnline(CallOnlineRequest request) {
        System.out.println("CallOnlineServiceImpl testPostCallOnline with url: " + configuration.getCallPostOnline());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CallOnlineRequest> requestHttpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<CallOnlineResponse> response = restTemplate.postForEntity(
                configuration.getCallPostOnline(),
                requestHttpEntity, CallOnlineResponse.class);

        return response.getBody();
    }

    @Override
    public CommerceResponse getListCommerceById(CommercePresentationRequest request) {
        System.out.println("====CallOnlineServiceImpl getListCommerceById with url: " + configuration.getGetListCommerce());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CommerceRequest commerceRequest = CommerceRequest.builder()
                .id(request.getId()).build();
        HttpEntity<CommerceRequest> requestHttpEntity = new HttpEntity<>(commerceRequest, headers);

        ResponseEntity<CommerceResponse> response = restTemplate.postForEntity(
                configuration.getGetListCommerce(),
                requestHttpEntity, CommerceResponse.class);

        return response.getBody();
    }

    @Override
    public EkycResponse compareFaceIntegration(FaceMatchingRequest request) {
        System.out.println("CallOnlineServiceImpl compareFaceIntegration with url: " + configuration.getEkycUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EkycRequest ekycRequest = EkycRequest.builder()
                .id(request.getId())
                .faceA(request.getFaceA())
                .faceB(request.getFaceB())
                .faceC(request.getFaceC())
                .faceD(request.getFaceD())
                .build();

        HttpEntity<EkycRequest> httpEntity = new HttpEntity<>(ekycRequest, headers);
        ResponseEntity<EkycResponse> response = restTemplate.postForEntity(
                configuration.getEkycUrl(), httpEntity, EkycResponse.class
        );
        return response.getBody();
    }

    @Override
    public FaceRetrievalResponse getListRetrieval(GetListRetrievalRequest request) {
        System.out.println("CallOnlineServiceImpl getListRetrieval with url: " + configuration.getEkycFaceRetrieval());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        FaceRetrievalRequest faceRetrievalRequest = FaceRetrievalRequest
                .builder()
                .id(request.getId())
                .faceA(request.getFaceA())
                .faceB(request.getFaceB())
                .faceC(request.getFaceC())
                .faceD(request.getFaceD())
                .build();

        HttpEntity<FaceRetrievalRequest> httpEntity = new HttpEntity<>(faceRetrievalRequest, headers);
        ResponseEntity<FaceRetrievalResponse> response = restTemplate.postForEntity(
                configuration.getEkycFaceRetrieval(), httpEntity, FaceRetrievalResponse.class
        );

        return response.getBody();
    }
}
