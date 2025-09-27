package com.example.onboarding.integration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallOnlineRequest {
    private String key;
}
