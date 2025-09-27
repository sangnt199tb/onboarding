package com.example.onboarding.presentation.controller;

import com.example.onboarding.presentation.exception.ErrorCode;
import com.example.onboarding.presentation.exception.OnboardingException;
import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.service.OnboardingService;
import com.example.onboarding.presentation.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/onboarding")
@RestController
public class OnboardingApiController {

    private final OnboardingService onboardingService;

    @Autowired
    public OnboardingApiController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @GetMapping("/check")
    public String testcontroller(){
        String phoneNumber = "0387501614";
        String email = "sangnguyen.94tb@gmail.com";
        return onboardingService.checkPhoneAndEmail(phoneNumber, email);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/check-phone-email")
    @ResponseStatus(HttpStatus.OK)
    public CheckPhoneAndEmailResponse postCheckPhoneAndEmail(@RequestBody CheckPhoneAndEmailRequest request,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(LogUtil.toJson(request));
        String id = onboardingService.checkPhoneAndEmail(request.getPhoneNumber(), request.getEmail());
        CheckPhoneAndEmailResponse response = new CheckPhoneAndEmailResponse();
        response.setId(id);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "find-by-phone")
    @ResponseStatus(HttpStatus.OK)
    public OnboardTransModel findTransactionByPhoneNumber(
            @RequestParam("phoneNumber") String phoneNumber, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return onboardingService.findTransactionByPhone(phoneNumber);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/compare-face-thread")
    @ResponseStatus(HttpStatus.OK)
    public FaceMatchingResponse compareFaceThread(@RequestBody FaceMatchingRequest request){
        return onboardingService.compareFaceThread(request);
    }

}
