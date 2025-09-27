package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.integration.model.kyc.FaceRetrievalKycResponse;
import com.example.onboarding.integration.model.kyc.LivenessCheckKycResponse;
import com.example.onboarding.integration.service.EkycService;
import com.example.onboarding.persistence.domain.OnboardingTransactionEntity;
import com.example.onboarding.persistence.repository.OnboardingTransactionRepo;
import com.example.onboarding.presentation.configuration.AppConfiguration;
import com.example.onboarding.presentation.model.FaceMatchingRequest;
import com.example.onboarding.presentation.model.FaceMatchingResponse;
import com.example.onboarding.presentation.model.OnboardTransModel;
import com.example.onboarding.presentation.service.OnboardingService;
import com.example.onboarding.presentation.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class OnboardingServiceImpl implements OnboardingService {
    private final OnboardingTransactionRepo onboardingTransactionRepo;
    private final EkycService ekycService;
    private final AppConfiguration appConfiguration;

    @Autowired
    @Qualifier("taskExecutor")
    private Executor taskExecutor;

    @Autowired
    public OnboardingServiceImpl(OnboardingTransactionRepo onboardingTransactionRepo, EkycService ekycService, AppConfiguration appConfiguration) {
        this.onboardingTransactionRepo = onboardingTransactionRepo;
        this.ekycService = ekycService;
        this.appConfiguration = appConfiguration;
    }

    @Override
    public String checkPhoneAndEmail(String phoneNumber, String email) {
        OnboardingTransactionEntity entity = new OnboardingTransactionEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setPhoneNumber(phoneNumber);
        entity.setEmail(email);
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        entity = onboardingTransactionRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public OnboardTransModel findTransactionByPhone(String phoneNumber) {
        OnboardingTransactionEntity entity = onboardingTransactionRepo.findFirstByPhoneNumberOrderByCreatedDateDesc(phoneNumber);
        OnboardTransModel onboardTransModel = new OnboardTransModel();
        onboardTransModel.setId(entity.getId());
        onboardTransModel.setAddress(entity.getAddress());
        onboardTransModel.setPhoneNumber(entity.getPhoneNumber());
        onboardTransModel.setEmail(entity.getEmail());
        onboardTransModel.setDob(entity.getDob());
        onboardTransModel.setExpirationDate(entity.getExpirationDate());
        onboardTransModel.setFullName(entity.getFullName());
        onboardTransModel.setIcNumber(entity.getIcNumber());
        onboardTransModel.setCreatedDate(entity.getCreatedDate());

        return onboardTransModel;
    }

    @Override
    public FaceMatchingResponse compareFaceThread(FaceMatchingRequest request) {
        try {
            CompletableFuture<FaceMatchingResponse> responseCompletableFuture = compareFaceThreadSum(request);
            return responseCompletableFuture.join();
        } catch (Exception e){
            System.out.println("OnboardingServiceImpl compareFaceThread with error detail:" + e);
            throw e;
        }
    }

    private CompletableFuture<com.example.onboarding.integration.model.kyc.FaceMatchingResponse> callCompareFaceThread(FaceMatchingRequest request){
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Start OnboardingServiceImpl callCompareFaceThread with thread name: " + Thread.currentThread().getName());
            com.example.onboarding.integration.model.kyc.FaceMatchingResponse faceMatchingResponse
                    = ekycService.compareFaceEkyc(request);
            System.out.println("End OnboardingServiceImpl callCompareFaceThread with thread name: " + Thread.currentThread().getName());
            return faceMatchingResponse;
        }, taskExecutor);
    }

    private CompletableFuture<LivenessCheckKycResponse> callLivenessCheckKycThread(FaceMatchingRequest request){
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Start OnboardingServiceImpl callLivenessCheckKycThread with thread name: " + Thread.currentThread().getName());
            LivenessCheckKycResponse livenessCheckKycResponse = ekycService.livenessCheckEkyc(request);
            System.out.println("End OnboardingServiceImpl callLivenessCheckKycThread with thread name: " + Thread.currentThread().getName());
            return livenessCheckKycResponse;
        }, taskExecutor);
    }

    private CompletableFuture<FaceRetrievalKycResponse> callFaceRetrievalKycThread(FaceMatchingRequest request){
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Start OnboardingServiceImpl callFaceRetrievalKycThread with thread name: " + Thread.currentThread().getName());
            FaceRetrievalKycResponse faceRetrievalKycResponse = ekycService.faceRetrievalEkyc(request);
            System.out.println("End OnboardingServiceImpl callFaceRetrievalKycThread with thread name: " + Thread.currentThread().getName());
            return faceRetrievalKycResponse;
        }, taskExecutor);
    }

    private CompletableFuture<FaceMatchingResponse> compareFaceThreadSum(FaceMatchingRequest request){
        try {
            CompletableFuture<com.example.onboarding.integration.model.kyc.FaceMatchingResponse> faceMatchingResponseCompletableFuture
                    = callCompareFaceThread(request);
            CompletableFuture<LivenessCheckKycResponse> livenessCheckKycResponseCompletableFuture
                    = callLivenessCheckKycThread(request);
            CompletableFuture<FaceRetrievalKycResponse> faceRetrievalKycResponseCompletableFuture
                    = callFaceRetrievalKycThread(request);

            return CompletableFuture.allOf(faceMatchingResponseCompletableFuture, livenessCheckKycResponseCompletableFuture, faceRetrievalKycResponseCompletableFuture)
                    .thenApply(v -> {
                        FaceMatchingResponse faceMatchingResponse = new FaceMatchingResponse();

                        // check face mathching
                        com.example.onboarding.integration.model.kyc.FaceMatchingResponse matchingResponse
                                = faceMatchingResponseCompletableFuture.join();
                        System.out.println("OnboardingServiceImpl compareFaceThreadSum matchingResponse: " + LogUtil.toJson(matchingResponse));
                        if(matchingResponse.isSuccess()){
                            faceMatchingResponse.setResultFaceMatching("PASS");
                        } else {
                            faceMatchingResponse.setResultFaceMatching("FAILED");
                        }

                        // liveness check
                        LivenessCheckKycResponse livenessCheckKycResponse = livenessCheckKycResponseCompletableFuture.join();
                        System.out.println("OnboardingServiceImpl compareFaceThreadSum livenessCheckKycResponse: " + LogUtil.toJson(livenessCheckKycResponse));
                        if(livenessCheckKycResponse.isSuccess()){
                            faceMatchingResponse.setResultLiveness("PASS");
                        } else {
                            faceMatchingResponse.setResultLiveness("FAILED");
                        }

                        // face retrieval
                        FaceRetrievalKycResponse retrievalKycResponse = faceRetrievalKycResponseCompletableFuture.join();
                        System.out.println("OnboardingServiceImpl compareFaceThreadSum retrievalKycResponse: " + LogUtil.toJson(retrievalKycResponse));
                        boolean resultFaceRetrieval = retrievalKycResponse.getMatched_faces().stream()
                                .anyMatch( e -> (e.getMatch_score() - Float.valueOf(appConfiguration.getThresholdScoreRetrival()) > 0));
                        if(!resultFaceRetrieval){
                            faceMatchingResponse.setResultFaceRetrival("PASS");
                        } else {
                            faceMatchingResponse.setResultFaceRetrival("FAILED");
                        }

                        return faceMatchingResponse;
                    });
        } catch (Exception e){
            System.out.println("OnboardingServiceImpl compareFaceThreadSum with error detail: " + e);
            throw e;
        }
    }

}
