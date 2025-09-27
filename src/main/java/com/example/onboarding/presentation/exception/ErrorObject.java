package com.example.onboarding.presentation.exception;

import lombok.Data;

@Data
public class ErrorObject {
    private String errorCode;
    private String errorDesc;
    private ErrorMessage messages;
}
