package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.integration.model.company.Company;
import com.example.onboarding.integration.service.CompanyServiceInter;
import com.example.onboarding.presentation.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyServiceInter companyServiceInter;

    @Autowired
    public CompanyServiceImpl(CompanyServiceInter companyServiceInter) {
        this.companyServiceInter = companyServiceInter;
    }

    @Override
    public Company getInfoCompany(String id) {
        return companyServiceInter.getInfoCompanyInter(id);
    }
}
