package com.example.onboarding.integration.service;

import com.example.onboarding.integration.model.CallOnlineRequest;
import com.example.onboarding.integration.model.CallOnlineResponse;
import com.example.onboarding.integration.model.CommerceResponse;
import com.example.onboarding.integration.model.ekyc.EkycResponse;
import com.example.onboarding.integration.model.ekyc.FaceRetrievalResponse;
import com.example.onboarding.presentation.model.CommercePresentationRequest;
import com.example.onboarding.presentation.model.FaceMatchingRequest;
import com.example.onboarding.presentation.model.GetListRetrievalRequest;

public interface CallOnlineService {
    CallOnlineResponse testPostCallOnline(CallOnlineRequest request);

    CommerceResponse getListCommerceById(CommercePresentationRequest request);

    EkycResponse compareFaceIntegration(FaceMatchingRequest request);

    FaceRetrievalResponse getListRetrieval(GetListRetrievalRequest request);
}
