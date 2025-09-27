package com.example.onboarding.presentation.controller;

import com.example.onboarding.integration.model.company.Company;
import com.example.onboarding.presentation.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get-info-by-id")
    @ResponseStatus(HttpStatus.OK)
    private Company getInfoCompany(String id){
        return companyService.getInfoCompany(id);
    }
}
