package com.example.onboarding.presentation.model.multithreadedtest;

import lombok.Data;

import java.util.List;

@Data
public class BankTransactionResponse {
    private List<String> recentTransactions;
}
