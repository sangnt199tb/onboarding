package com.example.onboarding.presentation.model;

import lombok.Data;

@Data
public class ErrorInfoSaveDb {
    private String errorCode;
    private String errorDesc;

    public ErrorInfoSaveDb(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
