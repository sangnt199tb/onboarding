package com.example.onboarding.presentation.model.multithreadedtest;

import lombok.Data;

@Data
public class SummaryResponse {
    private AccountingResponse accounting;
    private CreditCardResponse creditCard;
    private BankTransactionResponse bankTransaction;
    private CustomerResponse customer;
}
