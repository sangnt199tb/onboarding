package com.example.onboarding.integration.model.company;

import lombok.Data;

@Data
public class Milestone {
    private String name;
    private String dueDate;
    private Boolean completed;
}
