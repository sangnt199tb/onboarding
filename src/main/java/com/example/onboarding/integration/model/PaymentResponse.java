package com.example.onboarding.integration.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponse {
    private String method;
    private String status;
    private String transaction_id;
    private String paid_at;
    private BigDecimal total_amount;
    private String currency;
}
