package com.example.onboarding.presentation.type;

public enum ErrorCodeEnum {

    HYD35103("HYD-35-103","timeout"),
    HYD35104("HYD-35-104","khong hop le");

    private String errorCode;
    private String errorDesc;

    ErrorCodeEnum(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
}
