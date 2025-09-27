package com.example.onboarding.presentation.model.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountInfo {
    private String accountId;
    private double balance;
    private String status;
    private String createdAt;
    private List<TransactionInfo> transactions;
}
