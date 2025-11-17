package com.example.onboarding.presentation.controller;

import com.example.onboarding.integration.model.CallOnlineResponse;
import com.example.onboarding.integration.model.CommerceResponse;
import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.model.multithreadedtest.SummaryRequest;
import com.example.onboarding.presentation.model.multithreadedtest.SummaryResponse;
import com.example.onboarding.presentation.service.TestCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-collection")
public class TestCollection {

    private final TestCollectionService testCollectionService;

    private static final Logger logger = LoggerFactory.getLogger(TestCollection.class);


    @Autowired
    public TestCollection(TestCollectionService testCollectionService) {
        this.testCollectionService = testCollectionService;
    }

    @RequestMapping("/test-map")
    @GetMapping
    public boolean testMap(){
        logger.info("Ứng dụng đã khởi động thành công!");
        logger.debug("Đây là log DEBUG, chỉ hiện nếu cấu hình bật DEBUG cho package này");
        logger.warn("Đây là log WARNING, chú ý nhé!");
        logger.error("Đây là log ERROR, có lỗi rồi!");
        logger.info("xin chao");
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
        logger.warn("start TestCollection getListRetrieval: {}", request);
        return testCollectionService.getListRetrieval(request);
    }

    @RequestMapping("/get-test-thread")
    @PostMapping
    public SummaryResponse getTestThread(@RequestBody SummaryRequest request){
        return testCollectionService.getTestThread(request);
    }
}
