package com.example.onboarding.presentation.controller;

import com.example.onboarding.integration.model.CallOnlineResponse;
import com.example.onboarding.integration.model.CommerceResponse;
import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.model.multithreadedtest.SummaryRequest;
import com.example.onboarding.presentation.model.multithreadedtest.SummaryResponse;
import com.example.onboarding.presentation.service.TestCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-collection")
public class TestCollection {

    private final TestCollectionService testCollectionService;

    @Autowired
    public TestCollection(TestCollectionService testCollectionService) {
        this.testCollectionService = testCollectionService;
    }

    @RequestMapping("/test-map")
    @GetMapping
    public boolean testMap(){
        System.out.println("====Start TestCollection testMap====");
        testCollectionService.testCollection();
        return true;
    }

    @RequestMapping("/test-call-online")
    @GetMapping
    public CallOnlineResponse testCallOnline(){
        System.out.println("====Start TestCollection testCallOnline====");
        return testCollectionService.testCallOnline();

    }

    @RequestMapping("/get-all-list-commerce")
    @PostMapping
    public CommerceResponse getListCommerceById(@RequestBody CommercePresentationRequest request){
        System.out.println("====Start TestCollection getListCommerceById====");
        return testCollectionService.getListCommerceById(request);
    }

    @RequestMapping("/compare-face")
    @PostMapping
    public FaceMatchingResponse compareFace(@RequestBody FaceMatchingRequest request){
        System.out.println("====Start TestCollection compareFace====");
        return testCollectionService.compareFace(request);
    }

    @RequestMapping("/get-list-retrieval")
    @PostMapping
    public GetListRetrievalResponse getListRetrieval(@RequestBody GetListRetrievalRequest request){
        System.out.println("====Start TestCollection getListRetrieval====");
        return testCollectionService.getListRetrieval(request);
    }

    @RequestMapping("/get-test-thread")
    @PostMapping
    public SummaryResponse getTestThread(@RequestBody SummaryRequest request){
        return testCollectionService.getTestThread(request);
    }
}
