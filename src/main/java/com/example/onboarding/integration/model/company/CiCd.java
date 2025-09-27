package com.example.onboarding.integration.model.company;

import lombok.Data;

import java.util.List;

@Data
public class CiCd {
    private List<String> tools;
    private Boolean automated;
}
