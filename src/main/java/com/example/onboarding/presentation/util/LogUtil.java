package com.example.onboarding.presentation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LogUtil {
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object obj){
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e){
            return ToStringBuilder.reflectionToString(obj);
        }
    }
}
