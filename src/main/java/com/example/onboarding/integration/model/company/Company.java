package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class Company {
    private String id;
    private String name;
    private Integer founded;
    private Boolean active;
    private List<Employee> employees;
    private Department departments;
    private Meta meta;
}
