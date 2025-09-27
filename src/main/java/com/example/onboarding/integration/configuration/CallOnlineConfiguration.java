package com.example.onboarding.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tpb.integration")
@Data
public class CallOnlineConfiguration {
    private String callPostOnline;
    private String getListCommerce;
    private String ekycUrl;
    private String ekycFaceRetrieval;
    private String accountUrl;
    private String creditUrl;
    private String transactionUrl;
    private String customerUrl;
    private String ekycFaceMatching;
    private String ekycLivenessCheck;
    private String ekycFaceRetrievalCheck;
    private String getListAccountByUserId;
    private String getListTransactionByAccountOne;
    private String getListTransactionByAccountTwo;
    private String infoCompanyUrl;

}
