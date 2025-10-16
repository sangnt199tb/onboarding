package com.example.onboarding.integration.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class RequestContext {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public RequestContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
