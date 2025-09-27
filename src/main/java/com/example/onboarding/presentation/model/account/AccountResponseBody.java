package com.example.onboarding.presentation.model.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponseBody {
    private String userId;
    private List<AccountInfo> accounts;
}
