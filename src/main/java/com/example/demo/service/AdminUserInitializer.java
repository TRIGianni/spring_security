package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {
    @Bean
    public CommandLineRunner createAdminUser (UserDetailsRepository userDetailsRepository,
                                              PasswordEncoder passwordEncoder) {
        return (args) -> {
            if(userDetailsRepository.findByUsername("admin")==null){
                Users user = new Users();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRole("ROLE_ADMIN");

                userDetailsRepository.save(user);
                System.out.println(user.getUsername() + " has been created");
            }
        };
    }
}
