package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class Project {
    private String projectId;
    private String name;
    private String status;
    private List<String> technologies;
    private List<Milestone> milestones;
}
