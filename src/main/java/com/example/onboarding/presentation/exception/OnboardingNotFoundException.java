package com.example.onboarding.presentation.exception;

public class OnboardingNotFoundException extends TpbException{
    public OnboardingNotFoundException(String errorCode){
        super(errorCode);
    }
}
