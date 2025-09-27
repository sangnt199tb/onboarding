package com.example.onboarding.presentation.service;

import com.example.onboarding.integration.model.CallOnlineResponse;
import com.example.onboarding.integration.model.CommerceResponse;
import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.model.multithreadedtest.SummaryRequest;
import com.example.onboarding.presentation.model.multithreadedtest.SummaryResponse;

public interface TestCollectionService {
    void testCollection();

    CallOnlineResponse testCallOnline();

    CommerceResponse getListCommerceById(CommercePresentationRequest request);

    FaceMatchingResponse compareFace(FaceMatchingRequest request);

    GetListRetrievalResponse getListRetrieval(GetListRetrievalRequest request);

    SummaryResponse getTestThread(SummaryRequest request);
}
