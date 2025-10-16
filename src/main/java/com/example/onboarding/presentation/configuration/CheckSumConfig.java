package com.example.onboarding.presentation.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "check-sum")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckSumConfig {
    private String username;
    private String secureKey;
    private String apiId;
}
