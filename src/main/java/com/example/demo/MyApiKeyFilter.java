package com.example.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(-104)
@Component
public class MyApiKeyFilter implements Filter {

    private final String apiKey;

    private static final Logger log =
            LoggerFactory.getLogger(MyApiKeyFilter.class);

    public MyApiKeyFilter() {
        this.apiKey = Base64.getEncoder().encodeToString("password".getBytes(StandardCharsets.UTF_8));
        log.info("apiKey: {}", apiKey);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String apiKeyReceived = ((HttpServletRequest) request).getHeader("x-api-key");
        if (!apiKey.equals(apiKeyReceived)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid api key");
            return;
        }
        chain.doFilter(request, response);

    }
}
