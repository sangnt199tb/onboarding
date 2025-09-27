package com.example.onboarding.integration.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClothesResponse {
    private String product_id;
    private String name;
    private Integer quantity;
    private BigDecimal unit_price;
    private BigDecimal total_price;
}
