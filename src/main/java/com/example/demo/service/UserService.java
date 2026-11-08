package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public final UserDetailsRepository userDetailsRepository;
    public PasswordEncoder passwordEncoder;
    public UserService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        //Vérifier si l'utilisateur est déja présent
        if(userDetailsRepository.findByUsername(registerUserRequest.username())!=null){
            throw new RuntimeException("L'utilisateur est déjà présent");
        }
        //Créer l'utilisateur
        Users users = new Users();
        users.setUsername(registerUserRequest.username());
        users.setRole(Role.USER);
        users.setPassword(passwordEncoder.encode(registerUserRequest.password()));

        // sauvegarder l'utilisateur dans la DB
        Users user = userDetailsRepository.save(users);

        return new UserResponse(user.getId(),user.getUsername(),users.getRole().name());

    }

    public UserResponse createUserOrAdmin(CreateUserByAdminRequest createUserByAdminRequest) {
        if(userDetailsRepository.findByUsername(createUserByAdminRequest.username())!=null){
            throw new RuntimeException("L'utilisateur est déjà présent");
        }
        Users users = new Users();
        users.setUsername(createUserByAdminRequest.username());
        users.setRole(createUserByAdminRequest.role());
        users.setPassword(passwordEncoder.encode(createUserByAdminRequest.password()));
        Users user = userDetailsRepository.save(users);
        return new UserResponse(user.getId(),user.getUsername(),user.getRole().name());
    }
}
