package com.example.onboarding.integration.model;

import lombok.Data;

@Data
public class UserCommerceResponse {
    private String user_id;
    private String name;
    private String email;
    private String phone;
    private CommerceAddressResponse address;
}
