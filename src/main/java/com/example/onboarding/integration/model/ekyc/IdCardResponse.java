package com.example.onboarding.integration.model.ekyc;

import lombok.Data;

@Data
public class IdCardResponse {
    private String number;
    private String issued_by;
    private String issued_date;
}
