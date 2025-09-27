package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class Meta {
    private String created_at;
    private String last_updated;
    private List<String> tags;
    private String notes;
}
