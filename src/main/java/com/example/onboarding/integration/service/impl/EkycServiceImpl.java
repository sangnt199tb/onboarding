package com.example.onboarding.integration.service.impl;

import com.example.onboarding.integration.configuration.CallOnlineConfiguration;
import com.example.onboarding.integration.model.kyc.*;
import com.example.onboarding.integration.service.EkycService;
import com.example.onboarding.presentation.model.FaceMatchingRequest;
import com.example.onboarding.presentation.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

@Service
public class EkycServiceImpl implements EkycService {

    private final RestTemplate restTemplate;
    private final CallOnlineConfiguration configuration;

    @Autowired
    public EkycServiceImpl(RestTemplate restTemplate, CallOnlineConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
    }

    @Override
    public FaceMatchingResponse compareFaceEkyc(FaceMatchingRequest request) {
        System.out.println("Start EkycServiceImpl compareFaceEkyc request: " + LogUtil.toJson(request));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        com.example.onboarding.integration.model.kyc.FaceMatchingRequest faceMatchingRequest =
                com.example.onboarding.integration.model.kyc.FaceMatchingRequest
                        .builder()
                        .id(request.getId())
                        .faceA(request.getFaceA())
                        .faceB(request.getFaceB())
                        .faceC(request.getFaceC())
                        .faceD(request.getFaceD())
                        .build();
        HttpEntity<com.example.onboarding.integration.model.kyc.FaceMatchingRequest> httpEntity
                = new HttpEntity<>(faceMatchingRequest, headers);

        ResponseEntity<FaceMatchingResponse> response = restTemplate.postForEntity(
                configuration.getEkycFaceMatching(), httpEntity, FaceMatchingResponse.class
        );

        return response.getBody();
    }

    @Override
    public LivenessCheckKycResponse livenessCheckEkyc(FaceMatchingRequest request) {
        System.out.println("Start EkycServiceImpl livenessCheckEkyc request: " + LogUtil.toJson(request));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LivenessCheckKycRequest livenessCheckKycRequest = LivenessCheckKycRequest
                .builder()
                .id(request.getId())
                .faceA(request.getFaceA())
                .faceB(request.getFaceB())
                .faceC(request.getFaceC())
                .faceD(request.getFaceD())
                .build();

        HttpEntity<LivenessCheckKycRequest> httpEntity = new HttpEntity<>(livenessCheckKycRequest, headers);

        ResponseEntity<LivenessCheckKycResponse> response = restTemplate.postForEntity(
                configuration.getEkycLivenessCheck(), httpEntity, LivenessCheckKycResponse.class
        );

        return response.getBody();
    }

    @Override
    public FaceRetrievalKycResponse faceRetrievalEkyc(FaceMatchingRequest request) {
        System.out.println("Start EkycServiceImpl faceRetrievalEkyc request: " + LogUtil.toJson(request));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        FaceRetrievalKycRequest faceRetrievalKycRequest = FaceRetrievalKycRequest
                .builder()
                .id(request.getId())
                .faceA(request.getFaceA())
                .faceB(request.getFaceB())
                .faceC(request.getFaceC())
                .faceD(request.getFaceD())
                .build();

        HttpEntity<FaceRetrievalKycRequest> httpEntity = new HttpEntity<>(faceRetrievalKycRequest, headers);

        ResponseEntity<FaceRetrievalKycResponse> response = restTemplate.postForEntity(
                configuration.getEkycFaceRetrievalCheck(), httpEntity,  FaceRetrievalKycResponse.class
        );

        System.out.println("End EkycServiceImpl faceRetrievalEkyc response: " + LogUtil.toJson(response.getBody()));

        return response.getBody();
    }
}
