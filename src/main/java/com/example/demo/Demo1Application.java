package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

//    @Bean
//    @Order(SecurityFilterProperties.BASIC_AUTH_ORDER-1)
//    SecurityFilterChain apiKeySecurityFilterChain(HttpSecurity http) {
//        http.securityMatchers(
//                (matchers)->matchers.requestMatchers("/api-key/**"));
//        http.authorizeHttpRequests(
//                (requests) -> requests.anyRequest().authenticated());
//        http.addFilterBefore(new ApiKeyFilter(), AuthorizationFilter.class);
//        return http.build();
//    }

    @Bean
    @Order(SecurityFilterProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.formLogin(withDefaults());
//        http.httpBasic(withDefaults());
        return http.build();
    }
}
