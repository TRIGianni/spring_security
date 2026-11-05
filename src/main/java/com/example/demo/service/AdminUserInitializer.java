package com.example.demo.service;

import com.example.demo.entity.Role;
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
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(Role.ADMIN);

                userDetailsRepository.save(admin);
                System.out.println(admin.getUsername() + " has been created");
            }

            if(userDetailsRepository.findByUsername("user")==null){
                Users user = new Users();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRole(Role.USER);

                userDetailsRepository.save(user);
                System.out.println(user.getUsername() + " has been created");
            }
        };
    }
}
