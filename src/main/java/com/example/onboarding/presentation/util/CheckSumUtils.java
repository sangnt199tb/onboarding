package com.example.onboarding.presentation.util;

import com.example.onboarding.presentation.configuration.CheckSumConfig;
import com.example.onboarding.presentation.exception.ErrorCode;
import com.example.onboarding.presentation.exception.OnboardingException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckSumUtils {
    private static final Logger logger = LoggerFactory.getLogger(CheckSumUtils.class);

    @Autowired
    private CheckSumConfig checkSumConfig;

    public void validateCheckSumVneid(HttpServletRequest httpServletRequest){
        logger.info("Start CheckSumUtils validateCheckSumVneid");

        String apiId = httpServletRequest.getHeader("API_ID");
        String checkSum = httpServletRequest.getHeader("CHECK_SUM");
        String userName = httpServletRequest.getHeader("USER_NAME");

        if(!StringUtils.equalsIgnoreCase(apiId, checkSumConfig.getApiId())){
            throw new OnboardingException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if(!StringUtils.equalsIgnoreCase(userName, checkSumConfig.getUsername())){
            throw new OnboardingException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if(!checkSumVneid(checkSum, apiId, userName)){
            throw new OnboardingException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Boolean checkSumVneid(String checkSum, String apiId, String userName){
        String checkSumValue = apiId + userName + checkSumConfig.getSecureKey();
        if(StringUtils.equalsIgnoreCase(checkSum, DigestUtils.sha256Hex(checkSumValue))){
            return true;
        }
       return false;
    }
}
