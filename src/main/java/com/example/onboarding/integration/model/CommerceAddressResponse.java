package com.example.onboarding.integration.model;

import lombok.Data;

@Data
public class CommerceAddressResponse {
    private String line1;
    private String line2;
    private String city;
    private String postal_code;
    private String country;
}
