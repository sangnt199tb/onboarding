package com.example.onboarding.presentation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class OnboardTransModel {
    private String id;
    private String phoneNumber;
    private String icNumber;
    private String fullName;
    private String address;
    private String email;
    private String dob;
    private String dateOfIssue;
    private String expirationDate;
    private Timestamp createdDate;
    private String placeOfIssue;
    private Timestamp updateDate;
}
