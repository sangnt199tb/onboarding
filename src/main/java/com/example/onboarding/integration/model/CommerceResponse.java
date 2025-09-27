package com.example.onboarding.integration.model;

import lombok.Data;

import java.util.List;

@Data
public class CommerceResponse {
    private String order_id;
    private UserCommerceResponse user;
    private List<ClothesResponse> items;
    private PaymentResponse payment;
    private DeliveryResponse delivery;
    private List<OrderHistoryResponse> order_history;
}
