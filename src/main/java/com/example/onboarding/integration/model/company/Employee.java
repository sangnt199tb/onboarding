package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class Employee {
    private String id;
    private Name name;
    private List<String> roles;
    private Contact contact;
    private List<Project> projects;
}
