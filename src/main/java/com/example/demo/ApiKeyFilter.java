package com.example.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApiKeyFilter extends OncePerRequestFilter {

    private final String apiKey;
    private static final Logger log =
            LoggerFactory.getLogger(ApiKeyFilter.class);

    public ApiKeyFilter() {
        this.apiKey = Base64.getEncoder().
                encodeToString("passeword".getBytes(StandardCharsets.UTF_8));
        log.info("API KEY: {}", this.apiKey);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String KeyReceived = request.getHeader("x-api-key");
        if(!apiKey.equals(KeyReceived)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"API KEY mismatch");
            return;
        }

        // Marque la requête comme authentifiée
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken("api-client", null, List.of()));
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
