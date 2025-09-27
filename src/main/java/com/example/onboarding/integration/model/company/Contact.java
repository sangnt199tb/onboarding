package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class Contact {
    private String email;
    private List<Phone> phones;
}
