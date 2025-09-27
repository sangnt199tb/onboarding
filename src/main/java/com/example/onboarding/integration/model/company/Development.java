package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class Development {
    private List<String> frontend;
    private List<String> backend;
    private CiCd ci_cd;
}
